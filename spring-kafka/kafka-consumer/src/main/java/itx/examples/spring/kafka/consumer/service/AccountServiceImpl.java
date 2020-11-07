package itx.examples.spring.kafka.consumer.service;

import itx.examples.spring.kafka.dto.Account;
import itx.examples.spring.kafka.dto.AccountId;
import itx.examples.spring.kafka.events.CreateAccountAsyncEvent;
import itx.examples.spring.kafka.events.DeleteAccountAsyncEvent;
import itx.examples.spring.kafka.events.TransferFundsAsyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final Map<String, Account> accounts;

    public AccountServiceImpl() {
        this.accounts = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized boolean createAccount(CreateAccountAsyncEvent createAccountAsyncEvent) {
        if (!accounts.containsKey(createAccountAsyncEvent.getAccountId())) {
            LOGGER.info("Creating account {} {}", createAccountAsyncEvent.getAccountId(), createAccountAsyncEvent.getName());
            Account account = new Account(createAccountAsyncEvent.getAccountId(),
                    createAccountAsyncEvent.getName(),
                    createAccountAsyncEvent.getCredit());
            accounts.put(account.getAccountId(), account);
            return true;
        } else {
            LOGGER.error("Account already exists {} !", createAccountAsyncEvent.getName());
            return false;
        }
    }

    @Override
    public synchronized boolean deleteAccount(DeleteAccountAsyncEvent deleteAccountAsyncEvent) {
        LOGGER.info("Deleting account {}", deleteAccountAsyncEvent.getAccountId());
        return accounts.remove(deleteAccountAsyncEvent.getAccountId()) != null;
    }

    @Override
    public synchronized boolean transferFunds(TransferFundsAsyncEvent transferFundsAsyncEvent) {
        Account source = accounts.get(transferFundsAsyncEvent.getSourceAccountId());
        Account destination = accounts.get(transferFundsAsyncEvent.getDestinationAccountId());
        if (source != null && destination != null & source.getCredit() >= transferFundsAsyncEvent.getCredit()) {
            LOGGER.info("transferring funds {} -> {} {}", source.getName(), destination.getName(), transferFundsAsyncEvent.getCredit());
            Account sourceAfterTransaction = new Account(source.getAccountId(), source.getName(), (source.getCredit() - transferFundsAsyncEvent.getCredit()));
            Account destinationAfterTransaction = new Account(destination.getAccountId(), destination.getName(), (destination.getCredit() + transferFundsAsyncEvent.getCredit()));
            accounts.put(sourceAfterTransaction.getAccountId(),  sourceAfterTransaction);
            accounts.put(destinationAfterTransaction.getAccountId(),  destinationAfterTransaction);
            return true;
        } else {
            LOGGER.error("Transfer funds transaction {} has failed !", transferFundsAsyncEvent.getId());
            return false;
        }
    }

    @Override
    public synchronized Collection<Account> getAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }

    @Override
    public synchronized Optional<Account> getAccount(AccountId id) {
        return Optional.of(accounts.get(id.getId()));
    }

}
