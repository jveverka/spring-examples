package itx.examples.springboot.websockets.tests;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebSocketHandlerImpl implements WebSocketHandler {

    private final CountDownLatch clConnected;
    private final CountDownLatch clMessage;

    private WebSocketSession session;
    private String message;

    public WebSocketHandlerImpl() {
        this.clConnected = new CountDownLatch(1);
        this.clMessage = new CountDownLatch(1);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        this.clConnected.countDown();
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        TextMessage  textMessage = (TextMessage)message;
        if (textMessage.getPayload().startsWith("Echo: ")) {
            this.message = textMessage.getPayload();
            clMessage.countDown();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public boolean awaitConnected(long timeout, TimeUnit unit) throws InterruptedException {
        return clConnected.await(timeout, unit);
    }

    public void sendMessage(String message) throws IOException {
        session.sendMessage(new TextMessage(message));
    }

    public boolean awaitMessage(long timeout, TimeUnit unit) throws InterruptedException {
        return clMessage.await(timeout, unit);
    }

    public String getMessage() {
        return message;
    }

}
