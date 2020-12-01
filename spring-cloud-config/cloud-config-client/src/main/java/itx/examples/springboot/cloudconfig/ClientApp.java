package itx.examples.springboot.cloudconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ClientApp {

    private static final Logger LOG = LoggerFactory.getLogger(ClientApp.class);

    public static void main(String[] args) {
        LOG.info("Spring cloud client started ...");
        ConfigurableApplicationContext run = SpringApplication.run(ClientApp.class, args);
        run.registerShutdownHook();
    }

}
