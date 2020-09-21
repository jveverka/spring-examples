package itx.examples.apifirst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApiFirstApp {

    private static final Logger LOG = LoggerFactory.getLogger(ApiFirstApp.class);

    public static void main(String[] args) {
        LOG.info("API First App started");
        ConfigurableApplicationContext run = SpringApplication.run(ApiFirstApp.class, args);
        run.registerShutdownHook();
    }

}
