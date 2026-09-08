package pro.mrpc.mt5;

import org.junit.jupiter.api.Test;
import java.util.UUID;
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
    public void testGetIdProtoSerialization() {
        mt5_term_api.GetIdRequest req = mt5_term_api.GetIdRequest.newBuilder()
                .setUser("12345678")
                .setPassword("test_password")
                .build();
        assertEquals("12345678", req.getUser());
        assertEquals("test_password", req.getPassword());

        mt5_term_api.GetIdReply reply = mt5_term_api.GetIdReply.newBuilder()
                .setData(mt5_term_api.GetIdData.newBuilder().setId("68c935ee-a2b1-4f3e-bb36-3982845cfa85").build())
                .build();
        assertNotNull(reply.getData());
        assertEquals("68c935ee-a2b1-4f3e-bb36-3982845cfa85", reply.getData().getId());
    }
}
