package itx.examples.springboot.demo.controller;

import itx.examples.springboot.demo.dto.HealthStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/health")
public class HealthTestController {

    private static final Logger LOG = LoggerFactory.getLogger(HealthTestController.class);

    private String status = "UP";

    @GetMapping(path = "/test")
    public ResponseEntity<HealthStatus> getHealthTest() {
        LOG.info("getHealthTest: {}", status);
        return ResponseEntity.ok(new HealthStatus(status));
    }

    @PostMapping(path = "/test/{status}")
    public ResponseEntity<Void> setHealthTest(@PathVariable String status) {
        this.status = status;
        return ResponseEntity.ok().build();
    }

}
