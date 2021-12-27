package itx.examples.springnative.controller;

import itx.examples.springnative.dto.DataRequest;
import itx.examples.springnative.dto.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/data")
public class DataController {

    @GetMapping("/message")
    public ResponseEntity<DataResponse> getInfo() {
        return ResponseEntity.ok(new DataResponse(System.currentTimeMillis(), "Hi from Spring Native !"));
    }

    @PostMapping("/message")
    public ResponseEntity<DataResponse> postInfo(@RequestBody DataRequest request) {
        return ResponseEntity.ok(new DataResponse(System.currentTimeMillis(), request.message()));
    }

}
