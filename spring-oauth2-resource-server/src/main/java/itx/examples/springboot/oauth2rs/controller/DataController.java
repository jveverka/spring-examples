package itx.examples.springboot.oauth2rs.controller;

import itx.examples.springboot.oauth2rs.dto.ServerData;
import itx.examples.springboot.oauth2rs.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class DataController {

    private static final Logger LOG = LoggerFactory.getLogger(DataController.class);

    private final DataService dataService;

    public DataController(@Autowired DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/data")
    public ResponseEntity<ServerData> getData() {
        LOG.info("getData: ");
        return ResponseEntity.ok().body(dataService.getData());
    }

    @PostMapping("/data")
    public ResponseEntity<Void> setData(@RequestBody ServerData serverData) {
        LOG.info("setData: {}", serverData.getData());
        dataService.setData(serverData);
        return ResponseEntity.ok().build();
    }

}
