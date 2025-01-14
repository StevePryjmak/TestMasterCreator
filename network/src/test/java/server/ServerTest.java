package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    private Server server;
    private int port = 12345; // Define a port for testing

    @BeforeEach
    public void setUp() {
        server = new Server(port);
        Executors.newSingleThreadExecutor().submit(() -> server.startAcsept());
    }

    @AfterEach
    public void tearDown() {
        server.closeServer();
    }

    @Test
    public void testServerStartsAndAcceptsConnection() {
        assertDoesNotThrow(() -> {
            Socket socket = new Socket("localhost", port);
            assertTrue(socket.isConnected());
            socket.close();
        });
    }

    @Test
    public void testServerHandlesMultipleClients() throws Exception {
        Socket client1 = new Socket("localhost", port);
        Socket client2 = new Socket("localhost", port);

        assertTrue(client1.isConnected());
        assertTrue(client2.isConnected());

        client1.close();
        client2.close();
    }

    @Test
    @SuppressWarnings("unused")
    public void testServerDoesNotAcceptConnectionsAfterShutdown() {
        server.closeServer();
        assertThrows(IOException.class, () -> {
            try (Socket socket = new Socket("localhost", port)) {
            }
        });
    }
}
