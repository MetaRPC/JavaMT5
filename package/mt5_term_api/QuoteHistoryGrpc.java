package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ============================================================================
 * Quote history: the bars a chart is drawn from, and the ticks those bars are
 * built from, for one symbol - returned by PAGE, oldest first.
 * Both calls walk a time range forward. A page carries at most page_size items
 * and, when the range holds more, a next_page_token; passing that token back
 * (same symbol, timeframe/flags and range) returns the next page. An empty
 * next_page_token means the range is exhausted. Tokens are opaque: a client
 * stores and returns them, nothing else.
 * History lives in the terminal and is fetched from the broker on demand. The
 * first request for a symbol or a period the terminal has not loaded yet can
 * take a few seconds, and when the broker has not delivered it within that
 * time the call answers with an error whose error_code is
 * QUOTE_HISTORY_NOT_READY - ask again a moment later. Nothing is served from
 * a cache outside the terminal.
 * Every call needs the terminal id header ("id"), like every other call.
 * ============================================================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-quote-history.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class QuoteHistoryGrpc {

  private QuoteHistoryGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.QuoteHistory";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest,
      mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply> getBarsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Bars",
      requestType = mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest.class,
      responseType = mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest,
      mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply> getBarsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest, mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply> getBarsMethod;
    if ((getBarsMethod = QuoteHistoryGrpc.getBarsMethod) == null) {
      synchronized (QuoteHistoryGrpc.class) {
        if ((getBarsMethod = QuoteHistoryGrpc.getBarsMethod) == null) {
          QuoteHistoryGrpc.getBarsMethod = getBarsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest, mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Bars"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply.getDefaultInstance()))
              .setSchemaDescriptor(new QuoteHistoryMethodDescriptorSupplier("Bars"))
              .build();
        }
      }
    }
    return getBarsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest,
      mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply> getTicksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Ticks",
      requestType = mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest.class,
      responseType = mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest,
      mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply> getTicksMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest, mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply> getTicksMethod;
    if ((getTicksMethod = QuoteHistoryGrpc.getTicksMethod) == null) {
      synchronized (QuoteHistoryGrpc.class) {
        if ((getTicksMethod = QuoteHistoryGrpc.getTicksMethod) == null) {
          QuoteHistoryGrpc.getTicksMethod = getTicksMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest, mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Ticks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply.getDefaultInstance()))
              .setSchemaDescriptor(new QuoteHistoryMethodDescriptorSupplier("Ticks"))
              .build();
        }
      }
    }
    return getTicksMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QuoteHistoryStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryStub>() {
        @java.lang.Override
        public QuoteHistoryStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteHistoryStub(channel, callOptions);
        }
      };
    return QuoteHistoryStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QuoteHistoryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryBlockingStub>() {
        @java.lang.Override
        public QuoteHistoryBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteHistoryBlockingStub(channel, callOptions);
        }
      };
    return QuoteHistoryBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QuoteHistoryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteHistoryFutureStub>() {
        @java.lang.Override
        public QuoteHistoryFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteHistoryFutureStub(channel, callOptions);
        }
      };
    return QuoteHistoryFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ============================================================================
   * Quote history: the bars a chart is drawn from, and the ticks those bars are
   * built from, for one symbol - returned by PAGE, oldest first.
   * Both calls walk a time range forward. A page carries at most page_size items
   * and, when the range holds more, a next_page_token; passing that token back
   * (same symbol, timeframe/flags and range) returns the next page. An empty
   * next_page_token means the range is exhausted. Tokens are opaque: a client
   * stores and returns them, nothing else.
   * History lives in the terminal and is fetched from the broker on demand. The
   * first request for a symbol or a period the terminal has not loaded yet can
   * take a few seconds, and when the broker has not delivered it within that
   * time the call answers with an error whose error_code is
   * QUOTE_HISTORY_NOT_READY - ask again a moment later. Nothing is served from
   * a cache outside the terminal.
   * Every call needs the terminal id header ("id"), like every other call.
   * ============================================================================
   * </pre>
   */
  public static abstract class QuoteHistoryImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * OHLC bars (candles) of one symbol and timeframe, oldest first, by page.
     * </pre>
     */
    public void bars(mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBarsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Ticks of one symbol, oldest first, by page. MT5 keeps real tick history
     * (bid/ask/last with flags) for as far back as the broker provides it.
     * </pre>
     */
    public void ticks(mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTicksMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getBarsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest,
                mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply>(
                  this, METHODID_BARS)))
          .addMethod(
            getTicksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest,
                mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply>(
                  this, METHODID_TICKS)))
          .build();
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Quote history: the bars a chart is drawn from, and the ticks those bars are
   * built from, for one symbol - returned by PAGE, oldest first.
   * Both calls walk a time range forward. A page carries at most page_size items
   * and, when the range holds more, a next_page_token; passing that token back
   * (same symbol, timeframe/flags and range) returns the next page. An empty
   * next_page_token means the range is exhausted. Tokens are opaque: a client
   * stores and returns them, nothing else.
   * History lives in the terminal and is fetched from the broker on demand. The
   * first request for a symbol or a period the terminal has not loaded yet can
   * take a few seconds, and when the broker has not delivered it within that
   * time the call answers with an error whose error_code is
   * QUOTE_HISTORY_NOT_READY - ask again a moment later. Nothing is served from
   * a cache outside the terminal.
   * Every call needs the terminal id header ("id"), like every other call.
   * ============================================================================
   * </pre>
   */
  public static final class QuoteHistoryStub extends io.grpc.stub.AbstractAsyncStub<QuoteHistoryStub> {
    private QuoteHistoryStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteHistoryStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteHistoryStub(channel, callOptions);
    }

    /**
     * <pre>
     * OHLC bars (candles) of one symbol and timeframe, oldest first, by page.
     * </pre>
     */
    public void bars(mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBarsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Ticks of one symbol, oldest first, by page. MT5 keeps real tick history
     * (bid/ask/last with flags) for as far back as the broker provides it.
     * </pre>
     */
    public void ticks(mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTicksMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Quote history: the bars a chart is drawn from, and the ticks those bars are
   * built from, for one symbol - returned by PAGE, oldest first.
   * Both calls walk a time range forward. A page carries at most page_size items
   * and, when the range holds more, a next_page_token; passing that token back
   * (same symbol, timeframe/flags and range) returns the next page. An empty
   * next_page_token means the range is exhausted. Tokens are opaque: a client
   * stores and returns them, nothing else.
   * History lives in the terminal and is fetched from the broker on demand. The
   * first request for a symbol or a period the terminal has not loaded yet can
   * take a few seconds, and when the broker has not delivered it within that
   * time the call answers with an error whose error_code is
   * QUOTE_HISTORY_NOT_READY - ask again a moment later. Nothing is served from
   * a cache outside the terminal.
   * Every call needs the terminal id header ("id"), like every other call.
   * ============================================================================
   * </pre>
   */
  public static final class QuoteHistoryBlockingStub extends io.grpc.stub.AbstractBlockingStub<QuoteHistoryBlockingStub> {
    private QuoteHistoryBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteHistoryBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteHistoryBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * OHLC bars (candles) of one symbol and timeframe, oldest first, by page.
     * </pre>
     */
    public mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply bars(mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBarsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Ticks of one symbol, oldest first, by page. MT5 keeps real tick history
     * (bid/ask/last with flags) for as far back as the broker provides it.
     * </pre>
     */
    public mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply ticks(mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTicksMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ============================================================================
   * Quote history: the bars a chart is drawn from, and the ticks those bars are
   * built from, for one symbol - returned by PAGE, oldest first.
   * Both calls walk a time range forward. A page carries at most page_size items
   * and, when the range holds more, a next_page_token; passing that token back
   * (same symbol, timeframe/flags and range) returns the next page. An empty
   * next_page_token means the range is exhausted. Tokens are opaque: a client
   * stores and returns them, nothing else.
   * History lives in the terminal and is fetched from the broker on demand. The
   * first request for a symbol or a period the terminal has not loaded yet can
   * take a few seconds, and when the broker has not delivered it within that
   * time the call answers with an error whose error_code is
   * QUOTE_HISTORY_NOT_READY - ask again a moment later. Nothing is served from
   * a cache outside the terminal.
   * Every call needs the terminal id header ("id"), like every other call.
   * ============================================================================
   * </pre>
   */
  public static final class QuoteHistoryFutureStub extends io.grpc.stub.AbstractFutureStub<QuoteHistoryFutureStub> {
    private QuoteHistoryFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteHistoryFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteHistoryFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * OHLC bars (candles) of one symbol and timeframe, oldest first, by page.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply> bars(
        mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBarsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Ticks of one symbol, oldest first, by page. MT5 keeps real tick history
     * (bid/ask/last with flags) for as far back as the broker provides it.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply> ticks(
        mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTicksMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_BARS = 0;
  private static final int METHODID_TICKS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QuoteHistoryImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QuoteHistoryImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_BARS:
          serviceImpl.bars((mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryBarsReply>) responseObserver);
          break;
        case METHODID_TICKS:
          serviceImpl.ticks((mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiQuoteHistory.QuoteHistoryTicksReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class QuoteHistoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QuoteHistoryBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiQuoteHistory.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("QuoteHistory");
    }
  }

  private static final class QuoteHistoryFileDescriptorSupplier
      extends QuoteHistoryBaseDescriptorSupplier {
    QuoteHistoryFileDescriptorSupplier() {}
  }

  private static final class QuoteHistoryMethodDescriptorSupplier
      extends QuoteHistoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    QuoteHistoryMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (QuoteHistoryGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QuoteHistoryFileDescriptorSupplier())
              .addMethod(getBarsMethod())
              .addMethod(getTicksMethod())
              .build();
        }
      }
    }
    return result;
  }
}
