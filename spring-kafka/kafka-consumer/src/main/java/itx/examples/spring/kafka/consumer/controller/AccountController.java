package itx.examples.spring.kafka.consumer.controller;

import itx.examples.spring.kafka.consumer.service.AccountService;
import itx.examples.spring.kafka.dto.Account;
import itx.examples.spring.kafka.dto.AccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Collection;

@RestController
@RequestMapping("/services/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Account>> getAccount() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<Account> getAccount(@PathParam("account-id") String accountId) {
        return ResponseEntity.of(accountService.getAccount(new AccountId(accountId)));
    }

}
