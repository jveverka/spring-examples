package itx.examples.springboot.proxy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "proxy")
public class ProxyConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyConfig.class);

    private String targetUrl;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @PostConstruct
    public void init() {
        LOG.info("targetUrl={}", targetUrl);
    }

    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }

}
