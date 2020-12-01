package itx.examples.springboot.cloudconfig.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RefreshScope
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Value("${app.message:Hello default}")
    private String message;

    @PostConstruct
    public void init() {
        LOG.info("INIT: {}", message);
    }

    @GetMapping("/data")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("{ \"data\": \"" + message + "\" }");
    }

}
