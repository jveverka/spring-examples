package itx.examples.springboot.cloudconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;

@EnableConfigServer
@SpringBootApplication
public class ServerApp {

    private static final Logger LOG = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) {
        LOG.info("Spring Config server started ...");
        ConfigurableApplicationContext run = SpringApplication.run(ServerApp.class, args);
        run.registerShutdownHook();
    }

}
