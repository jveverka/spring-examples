package itx.examples.springnative.controller;

import itx.examples.springnative.dto.DataWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/data")
public class DataController {

    @GetMapping("/info")
    public ResponseEntity<DataWrapper> getInfo() {
        return ResponseEntity.ok(new DataWrapper("Hi"));
    }

}
