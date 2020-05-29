package itx.examples.springboot.websockets.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

public class WSPublisher implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(WSPublisher.class);

    private final Map<String, WebSocketSession> sessions;

    public WSPublisher(Map<String, WebSocketSession> sessions) {
        this.sessions = sessions;
    }

    @Override
    public void run() {
        for (WebSocketSession session: sessions.values()) {
            try {
                LOG.info("sending message to {}", session.getId());
                session.sendMessage(new TextMessage("generated message: " + System.currentTimeMillis()));
            } catch (IOException e) {
                LOG.error("WS Error: ", e);
            }
        }
    }

}
