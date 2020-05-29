package itx.examples.springbank.server.controller;

import itx.examples.springbank.common.dto.SystemInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/system")
public class SystemController {

    @GetMapping("/version")
    public SystemInfo getSystemInfo() {
        return new SystemInfo("1.0.0");
    }

}
