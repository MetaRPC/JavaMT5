package pro.mrpc.mt5;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import mt5_term_api.AccountHelperGrpc;
import mt5_term_api.ConnectionGrpc;
import mt5_term_api.MrpcMt5Error;
import mt5_term_api.Mt5TermApiAccountHelper;
import mt5_term_api.Mt5TermApiConnection;
import mt5_term_api.Mt5TermApiTradingHelper;
import mt5_term_api.TradingHelperGrpc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.mrpc.mt5.exceptions.ApiExceptionMT5;
import pro.mrpc.mt5.exceptions.TradeOutcomeUnknownExceptionMT5;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Retry rules of {@link MT5Account} against an in-process fake gateway:
 * trades are never re-sent after a transport failure, reads are, refused API keys fail fast.
 */
public class MT5AccountRetryTest {

    /** One scripted answer per call; the last one repeats. */
    private static final class Script<T> {
        private final Deque<Supplier<T>> steps = new ArrayDeque<>();
        final AtomicInteger calls = new AtomicInteger();

        @SafeVarargs
        final void set(Supplier<T>... answers) {
            steps.clear();
            for (Supplier<T> a : answers) steps.add(a);
        }

        void answer(StreamObserver<T> observer) {
            calls.incrementAndGet();
            Supplier<T> step = steps.size() > 1 ? steps.poll() : steps.peek();
            try {
                observer.onNext(step.get());
                observer.onCompleted();
            } catch (StatusRuntimeException e) {
                observer.onError(e);
            }
        }
    }

    private final Script<Mt5TermApiConnection.ConnectExReply> connectEx = new Script<>();
    private final Script<Mt5TermApiTradingHelper.OrderSendReply> orderSend = new Script<>();
    private final Script<Mt5TermApiTradingHelper.OrderModifyReply> orderModify = new Script<>();
    private final Script<Mt5TermApiTradingHelper.OrderCloseReply> orderClose = new Script<>();
    private final Script<Mt5TermApiAccountHelper.AccountSummaryReply> accountSummary = new Script<>();

    private Server server;
    private ManagedChannel channel;
    private MT5Account account;

    private static MrpcMt5Error.Error error(String code) {
        return MrpcMt5Error.Error.newBuilder().setErrorCode(code).setErrorMessage(code + " (test)").build();
    }

    private static <T> Supplier<T> fail(Status status) {
        return () -> { throw status.asRuntimeException(); };
    }

    private static Mt5TermApiConnection.ConnectExReply connected() {
        return Mt5TermApiConnection.ConnectExReply.newBuilder()
                .setData(Mt5TermApiConnection.ConnectData.newBuilder()
                        .setTerminalInstanceGuid("mt5_live_f6a35703d3905dcfbb64a23164b31f7b"))
                .build();
    }

    private static Mt5TermApiTradingHelper.OrderSendReply orderSendOk() {
        return Mt5TermApiTradingHelper.OrderSendReply.newBuilder()
                .setData(Mt5TermApiTradingHelper.OrderSendData.newBuilder().setReturnedCode(10009).setOrder(42))
                .build();
    }

    @BeforeEach
    void setUp() throws Exception {
        String name = InProcessServerBuilder.generateName();
        server = InProcessServerBuilder.forName(name).directExecutor()
                .addService(new ConnectionGrpc.ConnectionImplBase() {
                    @Override
                    public void connectEx(Mt5TermApiConnection.ConnectExRequest request,
                                          StreamObserver<Mt5TermApiConnection.ConnectExReply> observer) {
                        connectEx.answer(observer);
                    }
                })
                .addService(new TradingHelperGrpc.TradingHelperImplBase() {
                    @Override
                    public void orderSend(Mt5TermApiTradingHelper.OrderSendRequest request,
                                          StreamObserver<Mt5TermApiTradingHelper.OrderSendReply> observer) {
                        orderSend.answer(observer);
                    }

                    @Override
                    public void orderModify(Mt5TermApiTradingHelper.OrderModifyRequest request,
                                            StreamObserver<Mt5TermApiTradingHelper.OrderModifyReply> observer) {
                        orderModify.answer(observer);
                    }

                    @Override
                    public void orderClose(Mt5TermApiTradingHelper.OrderCloseRequest request,
                                           StreamObserver<Mt5TermApiTradingHelper.OrderCloseReply> observer) {
                        orderClose.answer(observer);
                    }
                })
                .addService(new AccountHelperGrpc.AccountHelperImplBase() {
                    @Override
                    public void accountSummary(Mt5TermApiAccountHelper.AccountSummaryRequest request,
                                               StreamObserver<Mt5TermApiAccountHelper.AccountSummaryReply> observer) {
                        accountSummary.answer(observer);
                    }
                })
                .build().start();
        channel = InProcessChannelBuilder.forName(name).directExecutor().build();
        account = new MT5Account(1L, "pw", "in-process", "test-key", null, channel);

        connectEx.set(MT5AccountRetryTest::connected);
        account.connectByServerName("Test-Server", null, 0);
        connectEx.calls.set(0);
    }

    @AfterEach
    void tearDown() {
        channel.shutdownNow();
        server.shutdownNow();
    }

    @Test
    void orderSendUnavailableIsNotResent() {
        orderSend.set(fail(Status.UNAVAILABLE.withDescription("connection reset")));

        TradeOutcomeUnknownExceptionMT5 ex = assertThrows(TradeOutcomeUnknownExceptionMT5.class,
                () -> account.orderSend(Mt5TermApiTradingHelper.OrderSendRequest.getDefaultInstance()));

        assertEquals(1, orderSend.calls.get(), "OrderSend must be sent exactly once");
        assertEquals(0, connectEx.calls.get(), "no reconnect after an unknown trade outcome");
        assertEquals("TRADE_OUTCOME_UNKNOWN", ex.getErrorCode());
        assertEquals("OrderSend", ex.getOperation());
        assertEquals(Status.Code.UNAVAILABLE, ex.getStatusCode());
        assertInstanceOf(StatusRuntimeException.class, ex.getCause());
        assertTrue(ex.getMessage().startsWith("TRADE_OUTCOME_UNKNOWN: OrderSend failed with gRPC UNAVAILABLE"),
                ex.getMessage());
        assertInstanceOf(ApiExceptionMT5.class, ex, "existing catch (ApiExceptionMT5) handlers still catch it");
    }

    @Test
    void orderModifyAndCloseOnDeadlineInternalUnknownAreNotResent() {
        orderModify.set(fail(Status.DEADLINE_EXCEEDED));
        TradeOutcomeUnknownExceptionMT5 m = assertThrows(TradeOutcomeUnknownExceptionMT5.class,
                () -> account.orderModify(Mt5TermApiTradingHelper.OrderModifyRequest.getDefaultInstance()));
        assertEquals("OrderModify", m.getOperation());
        assertEquals(Status.Code.DEADLINE_EXCEEDED, m.getStatusCode());
        assertEquals(1, orderModify.calls.get());

        orderClose.set(fail(Status.INTERNAL));
        TradeOutcomeUnknownExceptionMT5 c = assertThrows(TradeOutcomeUnknownExceptionMT5.class,
                () -> account.orderClose(7L, 0.01, 10));
        assertEquals("OrderClose", c.getOperation());
        assertEquals(1, orderClose.calls.get());

        orderClose.calls.set(0);
        orderClose.set(fail(Status.UNKNOWN));
        assertThrows(TradeOutcomeUnknownExceptionMT5.class, () -> account.orderClose(7L, 0.01, 10));
        assertEquals(1, orderClose.calls.get());
        assertEquals(0, connectEx.calls.get());
    }

    @Test
    void orderSendTerminalInstanceNotFoundReconnectsAndResends() throws Exception {
        orderSend.set(
                () -> Mt5TermApiTradingHelper.OrderSendReply.newBuilder()
                        .setError(error("TERMINAL_INSTANCE_NOT_FOUND")).build(),
                MT5AccountRetryTest::orderSendOk);

        Mt5TermApiTradingHelper.OrderSendReply reply =
                account.orderSend(Mt5TermApiTradingHelper.OrderSendRequest.getDefaultInstance());

        assertEquals(42, reply.getData().getOrder());
        assertEquals(2, orderSend.calls.get());
        assertEquals(1, connectEx.calls.get());
    }

    @Test
    void readUnavailableIsRetriedAfterReconnect() throws Exception {
        accountSummary.set(
                fail(Status.UNAVAILABLE),
                () -> Mt5TermApiAccountHelper.AccountSummaryReply.newBuilder()
                        .setData(Mt5TermApiAccountHelper.AccountSummaryData.newBuilder().setAccountBalance(1000))
                        .build());

        Mt5TermApiAccountHelper.AccountSummaryReply reply = account.accountSummary();

        assertEquals(1000, reply.getData().getAccountBalance());
        assertEquals(2, accountSummary.calls.get());
        assertEquals(1, connectEx.calls.get());
    }

    @Test
    void apiKeyInvalidInReplyFailsFastWithCode() {
        orderSend.set(() -> Mt5TermApiTradingHelper.OrderSendReply.newBuilder()
                .setError(error("API_KEY_INVALID")).build());

        ApiExceptionMT5 ex = assertThrows(ApiExceptionMT5.class,
                () -> account.orderSend(Mt5TermApiTradingHelper.OrderSendRequest.getDefaultInstance()));

        assertEquals("API_KEY_INVALID", ex.getErrorCode());
        assertEquals(1, orderSend.calls.get());
        assertEquals(0, connectEx.calls.get());
    }

    @Test
    void apiKeyInvalidInStatusFailsFastWithCode() {
        accountSummary.set(fail(Status.UNAVAILABLE.withDescription("API_KEY_INVALID: unknown key")));

        ApiExceptionMT5 ex = assertThrows(ApiExceptionMT5.class, () -> account.accountSummary());

        assertEquals("API_KEY_INVALID", ex.getErrorCode());
        assertEquals(1, accountSummary.calls.get());
        assertEquals(0, connectEx.calls.get());
    }

    @Test
    void readReconnectRefusedKeyStopsAndKeepsCode() {
        accountSummary.set(fail(Status.UNAVAILABLE));
        connectEx.set(() -> Mt5TermApiConnection.ConnectExReply.newBuilder()
                .setError(error("API_KEY_REQUIRED")).build());

        ApiExceptionMT5 ex = assertThrows(ApiExceptionMT5.class, () -> account.accountSummary());

        assertEquals("API_KEY_REQUIRED", ex.getErrorCode());
        assertTrue(ex.getMessage().startsWith("gRPC unavailable and reconnection failed: "), ex.getMessage());
        assertEquals(1, accountSummary.calls.get(), "no further calls after a refused key");
        assertEquals(1, connectEx.calls.get(), "no reconnect loop after a refused key");
    }

    @Test
    void apiKeyCodesAreTerminalLoginErrors() {
        assertTrue(MT5Account.isTerminalLoginError(new ApiExceptionMT5(error("API_KEY_INVALID"))));
        assertTrue(MT5Account.isTerminalLoginError(new ApiExceptionMT5(error("API_KEY_REQUIRED"))));
        assertFalse(MT5Account.isTerminalLoginError(new ApiExceptionMT5(error("TERMINAL_INSTANCE_NOT_FOUND"))));
    }
}
