package itx.examples.springboot.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		LOG.info("Spring demo started");
		ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
		run.registerShutdownHook();
	}

}
