package itx.examples.spring.kafka.producer.controller;

import itx.examples.spring.kafka.dto.AccountId;
import itx.examples.spring.kafka.dto.CreateAccountRequest;
import itx.examples.spring.kafka.dto.DepositAccountRequest;
import itx.examples.spring.kafka.dto.TransferFundsRequest;
import itx.examples.spring.kafka.events.CreateAccountAsyncEvent;
import itx.examples.spring.kafka.events.DeleteAccountAsyncEvent;
import itx.examples.spring.kafka.events.DepositAccountAsyncEvent;
import itx.examples.spring.kafka.events.TransferFundsAsyncEvent;
import itx.examples.spring.kafka.producer.service.EventProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/services/accounts")
public class AccountController {

    private final EventProducer eventProducer;

    public AccountController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @PostMapping()
    public ResponseEntity<AccountId> createAccount(@RequestBody CreateAccountRequest request) {
        String accountId = UUID.randomUUID().toString();
        CreateAccountAsyncEvent createAccountAsyncEvent = new CreateAccountAsyncEvent(
                UUID.randomUUID().toString(), accountId, request.getName(), request.getCredit()
        );
        eventProducer.sendAccountMessage(createAccountAsyncEvent);
        return ResponseEntity.ok(new AccountId(accountId));
    }

    @PutMapping("/deposit")
    public ResponseEntity<Void> depositAccount(@RequestBody DepositAccountRequest request) {
        DepositAccountAsyncEvent depositAccountAsyncEvent = new DepositAccountAsyncEvent(
                UUID.randomUUID().toString(), request.getAccountId(), request.getCredit()
        );
        eventProducer.sendAccountMessage(depositAccountAsyncEvent);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{account-id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("account-id") String accountId) {
        DeleteAccountAsyncEvent deleteAccountAsyncEvent = new DeleteAccountAsyncEvent(UUID.randomUUID().toString(), accountId);
        eventProducer.sendAccountMessage(deleteAccountAsyncEvent);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> transferFunds(@RequestBody TransferFundsRequest request) {
        TransferFundsAsyncEvent transferFundsAsyncEvent = new TransferFundsAsyncEvent(UUID.randomUUID().toString(),
                request.getSourceAccountId(), request.getDestinationAccountId(), request.getCredit());
        eventProducer.sendAccountMessage(transferFundsAsyncEvent);
        return ResponseEntity.ok().build();
    }

}
