package itx.examples.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebFluxApp {

    private static final Logger LOG = LoggerFactory.getLogger(WebFluxApp.class);

    public static void main(String[] args) {
        LOG.info("WebFlux App started");
        ConfigurableApplicationContext run = SpringApplication.run(WebFluxApp.class, args);
        run.registerShutdownHook();
    }

}
