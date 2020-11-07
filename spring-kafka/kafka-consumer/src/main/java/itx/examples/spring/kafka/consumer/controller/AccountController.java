package itx.examples.spring.kafka.consumer.controller;

import itx.examples.spring.kafka.dto.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/services/accounts")
public class AccountController {

    @GetMapping()
    public ResponseEntity<List<Account>> getAccount() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<Account> getAccount(@PathParam("account-id") String accountId) {
        return ResponseEntity.ok().build();
    }

}
