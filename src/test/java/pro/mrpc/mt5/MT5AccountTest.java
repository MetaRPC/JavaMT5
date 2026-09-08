package pro.mrpc.mt5;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import mt5_term_api.Mt5TermApiConnection;
import static org.junit.jupiter.api.Assertions.*;

public class MT5AccountTest {

    @Test
    public void testAccountInitialization() {
        UUID id = UUID.randomUUID();
        MT5Account account = new MT5Account(12345678L, "test_password", "mt5.mrpc.pro:443", id);

        assertNotNull(account);
        assertEquals(12345678L, account.getUser());
        assertEquals("test_password", account.getPassword());
        assertEquals("mt5.mrpc.pro:443", account.getGrpcServer());
        assertEquals(id, account.getId());
        assertFalse(account.isConnected());
    }

    @Test
    public void testServiceWrappers() {
        MT5Account account = new MT5Account(12345678L, "test_password", "mt5.mrpc.pro:443", null);
        MT5Service service = new MT5Service(account);
        assertNotNull(service);

        MT5Sugar sugar = new MT5Sugar(service);
        assertNotNull(sugar);
    }

    @Test
    public void testConnectRequestProtoSerialization() {
        Mt5TermApiConnection.ConnectRequest req = Mt5TermApiConnection.ConnectRequest.newBuilder()
                .setUser(12345678L)
                .setPassword("test_password")
                .setHost("mt5.mrpc.pro")
                .setPort(443)
                .build();
        assertEquals(12345678L, req.getUser());
        assertEquals("test_password", req.getPassword());
        assertEquals("mt5.mrpc.pro", req.getHost());
        assertEquals(443, req.getPort());
    }

    @Test
    public void testDeterministicAccountId() {
        long user = 12345678L;
        String pass = "test_password";
        UUID id1 = UUID.nameUUIDFromBytes((user + ":" + pass).getBytes(StandardCharsets.UTF_8));
        UUID id2 = UUID.nameUUIDFromBytes((user + ":" + pass).getBytes(StandardCharsets.UTF_8));
        assertEquals(id1, id2);
        assertNotNull(id1);

        MT5Account account = new MT5Account(user, pass, "mt5.mrpc.pro:443", id1);
        assertEquals(id1, account.getId());
    }
}
