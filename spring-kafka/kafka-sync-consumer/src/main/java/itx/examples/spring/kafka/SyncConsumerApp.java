package itx.examples.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SyncConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(SyncConsumerApp.class, args);
    }

}
