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
        return userService.create(createUserData);
    }

    @GetMapping("/{id}")
    private Mono<UserData> getEmployeeById(@PathVariable String id) {
        return userService.getEmployee(id);
    }

    @GetMapping
    private Flux<UserData> getAllEmployees() {
        return userService.getAll();
    }

    @PutMapping()
    private Mono<UserData> updateEmployee(@RequestBody UserData employee) {
        return userService.update(employee);
    }

    @DeleteMapping("/{id}")
    private Mono<UserData> deleteEmployee(@PathVariable String id) {
        return userService.delete(id);
    }

}
