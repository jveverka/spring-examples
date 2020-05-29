package itx.examples.springbank.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerApp {

    private static final Logger LOG = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) {
        LOG.info("Spring bank started");
        ConfigurableApplicationContext run = SpringApplication.run(ServerApp.class, args);
        run.registerShutdownHook();
    }

}
