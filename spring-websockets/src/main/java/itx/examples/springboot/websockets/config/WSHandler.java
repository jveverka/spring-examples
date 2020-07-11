package itx.examples.springboot.websockets.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WSHandler implements WebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WSHandler.class);

    private final Map<String, WebSocketSession> sessions;
    private final ScheduledExecutorService executorService;

    public WSHandler() {
        this.sessions = new ConcurrentHashMap<>();
        this.executorService = Executors.newScheduledThreadPool(1);
        this.executorService.scheduleWithFixedDelay(new WSPublisher(this.sessions), 0L, 20L, TimeUnit.SECONDS);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOG.info("WS session: {}", session.getId());
        this.sessions.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        TextMessage  textMessage = (TextMessage)message;
        String payload = textMessage.getPayload();
        LOG.info("WS message: {} message={}", session.getId(), payload);
        session.sendMessage(new TextMessage("Echo: " + payload));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOG.info("WS transport error: {}", session.getId());
        this.sessions.remove(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        LOG.info("WS connection closed: {}", session.getId());
        this.sessions.remove(session.getId(), session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @PreDestroy
    public void close() throws Exception {
        LOG.info("WS shutdown");
        this.executorService.shutdownNow();
    }

}
