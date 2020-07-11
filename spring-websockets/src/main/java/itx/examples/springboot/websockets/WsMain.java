package itx.examples.springboot.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WsMain {

    private static final Logger LOG = LoggerFactory.getLogger(WsMain.class);

    public static void main(String[] args) {
        LOG.info("Spring web-socket demo started");
        ConfigurableApplicationContext run = SpringApplication.run(WsMain.class, args);
        run.registerShutdownHook();
    }

}
