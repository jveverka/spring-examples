package itx.examples.springboot.websockets.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WSTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testWebSocket() throws InterruptedException, IOException {
        String wsUri = "ws://localhost:" + port + "/messages";
        WebSocketHandlerImpl webSocketHandler = new WebSocketHandlerImpl();
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(new StandardWebSocketClient(), webSocketHandler, wsUri);
        connectionManager.start();
        webSocketHandler.awaitConnected(10, TimeUnit.SECONDS);
        webSocketHandler.sendMessage("Test Message");
        webSocketHandler.awaitMessage(10, TimeUnit.SECONDS);
        String message = webSocketHandler.getMessage();
        assertEquals("Echo: Test Message", message);
    }

}
