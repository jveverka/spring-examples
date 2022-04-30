package itx.examples.springboot.websockets.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class WSTerminalSession implements Runnable, Consumer<String> {

    private static final Logger LOG = LoggerFactory.getLogger(WSTerminalSession.class);

    private final WebSocketSession session;

    public WSTerminalSession(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            this.session.sendMessage(new TextMessage("sh -c ls -la\r\n"));
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("sh", "-c", "ls -la");
            builder.directory(new File(System.getProperty("user.home")));
            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), this::accept);
            executorService.submit(streamGobbler);
            int exitCode = process.waitFor();
            assert exitCode == 0;
        } catch (IOException e) {
            LOG.error("IOException: ", e);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException: ", e);
        } finally {
            executorService.shutdownNow();
        }
    }

    @Override
    public void accept(String message) {
        Executors.newSingleThreadExecutor().submit(new WSSender(session, message));
        //this.session.sendMessage(new TextMessage(message));
    }

    private class WSSender implements Runnable {

        private final WebSocketSession session;
        private final String message;

        public WSSender(WebSocketSession session, String message) {
            this.session = session;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                this.session.sendMessage(new TextMessage(message + "\r\n"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
