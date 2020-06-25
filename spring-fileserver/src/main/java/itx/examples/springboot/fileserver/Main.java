package itx.examples.springboot.fileserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("Spring file server demo started");
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
        run.registerShutdownHook();
    }

}
