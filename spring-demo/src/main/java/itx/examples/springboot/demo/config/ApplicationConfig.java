package itx.examples.springboot.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ApplicationConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    private final String id;

    public ApplicationConfig() {
        this.id = UUID.randomUUID().toString();
        LOG.info("#APPID: {}", id);
    }

    public String getId() {
        return id;
    }

}
