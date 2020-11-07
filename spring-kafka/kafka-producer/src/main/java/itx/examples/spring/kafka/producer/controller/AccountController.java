package itx.examples.spring.kafka.producer.controller;

import itx.examples.spring.kafka.dto.AccountId;
import itx.examples.spring.kafka.dto.CreateAccountRequest;
import itx.examples.spring.kafka.dto.MessageRequest;
import itx.examples.spring.kafka.dto.TransferFundsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/services/accounts")
public class AccountController {

    @PostMapping()
    public ResponseEntity<AccountId> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{account-id}")
    public ResponseEntity<Void> deleteAccount(@PathParam("account-id") String accountId) {
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> transferFunds(@PathParam("account-id") TransferFundsRequest request) {
        return ResponseEntity.ok().build();
    }

}
