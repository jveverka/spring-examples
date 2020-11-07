package itx.examples.spring.kafka.consumer.service;

import itx.examples.spring.kafka.dto.Account;
import itx.examples.spring.kafka.dto.AccountId;
import itx.examples.spring.kafka.events.CreateAccountAsyncEvent;
import itx.examples.spring.kafka.events.DeleteAccountAsyncEvent;
import itx.examples.spring.kafka.events.TransferFundsAsyncEvent;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {

    boolean createAccount(CreateAccountAsyncEvent createAccountAsyncEvent);

    boolean deleteAccount(DeleteAccountAsyncEvent deleteAccountAsyncEvent);

    boolean transferFunds(TransferFundsAsyncEvent transferFundsAsyncEvent);

    Collection<Account> getAccounts();

    Optional<Account> getAccount(AccountId id);

}
