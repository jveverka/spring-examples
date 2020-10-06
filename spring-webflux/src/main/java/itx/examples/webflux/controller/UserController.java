package itx.examples.webflux.controller;

import itx.examples.webflux.dto.UserData;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    private Mono<UserData> getEmployeeById(@PathVariable String id) {
        return null;
    }

    @GetMapping
    private Flux<UserData> getAllEmployees() {
        return null;
    }

    @PostMapping()
    private Mono<UserData> updateEmployee(@RequestBody UserData employee) {
        return null;
    }

}
