package itx.examples.springboot.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ApplicationConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    private final String id;
    private final long startTime;

    public ApplicationConfig() {
        this.id = UUID.randomUUID().toString();
        this.startTime = System.currentTimeMillis();
        LOG.info("#APPID: {}", id);
        LOG.info("#START: {}", startTime);
    }

    public String getId() {
        return id;
    }

    public long getStartTime() {
        return startTime;
    }

}
