package itx.examples.spring.kafka.consumer.service;

import itx.examples.spring.kafka.dto.Account;
import itx.examples.spring.kafka.dto.AccountId;
import itx.examples.spring.kafka.events.CreateAccountAsyncEvent;
import itx.examples.spring.kafka.events.DeleteAccountAsyncEvent;
import itx.examples.spring.kafka.events.DepositAccountAsyncEvent;
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
        Account account = accounts.remove(deleteAccountAsyncEvent.getAccountId());
        if (account != null) {
            LOGGER.error("Account {} not found !", deleteAccountAsyncEvent.getAccountId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean transferFunds(TransferFundsAsyncEvent transferFundsAsyncEvent) {
        Account source = accounts.get(transferFundsAsyncEvent.getSourceAccountId());
        Account destination = accounts.get(transferFundsAsyncEvent.getDestinationAccountId());
        if (source != null && destination != null && source.getCredit() >= transferFundsAsyncEvent.getCredit()) {
            LOGGER.info("transferring funds {} -> {} {}", source.getName(), destination.getName(), transferFundsAsyncEvent.getCredit());
            Account sourceAfterTransaction = new Account(source.getAccountId(), source.getName(), (source.getCredit() - transferFundsAsyncEvent.getCredit()));
            Account destinationAfterTransaction = new Account(destination.getAccountId(), destination.getName(), (destination.getCredit() + transferFundsAsyncEvent.getCredit()));
            accounts.put(sourceAfterTransaction.getAccountId(), sourceAfterTransaction);
            accounts.put(destinationAfterTransaction.getAccountId(), destinationAfterTransaction);
            return true;
        } else {
            LOGGER.error("Transfer funds transaction {} has failed !", transferFundsAsyncEvent.getSourceAccountId());
            return false;
        }
    }

    @Override
    public boolean depositFunds(DepositAccountAsyncEvent depositAccountAsyncEvent) {
        Account account = accounts.get(depositAccountAsyncEvent.getAccountId());
        if (account != null && (account.getCredit() + depositAccountAsyncEvent.getCredit()) > 0) {
            Account accountAfterTransaction = new Account(account.getAccountId(), account.getName(), (account.getCredit() + depositAccountAsyncEvent.getCredit()));
            accounts.put(accountAfterTransaction.getAccountId(), accountAfterTransaction);
            return true;
        } else {
            LOGGER.error("Deposit has failed {} !", depositAccountAsyncEvent.getAccountId());
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
