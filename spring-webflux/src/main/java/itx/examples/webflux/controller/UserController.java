package itx.examples.webflux.controller;

import itx.examples.webflux.dto.CreateUserData;
import itx.examples.webflux.dto.UserData;
import itx.examples.webflux.services.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    private Mono<UserData> createEmployee(@RequestBody CreateUserData createUserData) {
        return null;
    }

    @GetMapping("/{id}")
    private Mono<UserData> getEmployeeById(@PathVariable String id) {
        return null;
    }

    @GetMapping
    private Flux<UserData> getAllEmployees() {
        return null;
    }

    @PutMapping()
    private Mono<UserData> updateEmployee(@RequestBody UserData employee) {
        return null;
    }

    @DeleteMapping("/{id}")
    private Mono<UserData> deleteEmployee(@PathVariable String id) {
        return null;
    }

}
