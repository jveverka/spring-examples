package itx.examples.springbank.server.service;

import itx.examples.springbank.common.dto.Client;
import itx.examples.springbank.common.dto.ClientId;
import itx.examples.springbank.common.dto.DepositRequest;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.TransactionRequest;
import itx.examples.springbank.common.dto.WithdrawRequest;
import itx.examples.springbank.common.service.BankService;
import itx.examples.springbank.server.repository.AccountRepository;
import itx.examples.springbank.server.repository.ClientRepository;
import itx.examples.springbank.server.repository.model.AccountEntity;
import itx.examples.springbank.server.repository.model.ClientEntity;
import itx.examples.springbank.server.utils.DTS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public BankServiceImpl(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public void transferFunds(TransactionRequest transactionRequest) throws ServiceException {
        Long sourceAccountId = Long.parseLong(transactionRequest.getSourceAccountId().getId());
        Optional<AccountEntity> sourceAccountOptional = accountRepository.findById(sourceAccountId);
        if (sourceAccountOptional.isPresent()) {
            Long targetAccountId = Long.parseLong(transactionRequest.getTargetAccountId().getId());
            Optional<AccountEntity> targetAccountOptional = accountRepository.findById(targetAccountId);
            if (targetAccountOptional.isPresent()) {
                AccountEntity sourceAccount = sourceAccountOptional.get();
                AccountEntity targetAccount = targetAccountOptional.get();
                Long amount = transactionRequest.getAmount();
                if (sourceAccount.getCredit() >= amount) {
                    sourceAccount.setCredit(sourceAccount.getCredit() - amount);
                    targetAccount.setCredit(targetAccount.getCredit() + amount);
                    accountRepository.save(sourceAccount);
                    accountRepository.save(targetAccount);
                } else {
                    //not enough credit on source account
                    throw new ServiceException();
                }
            } else {
                //target account does not exist
                throw new ServiceException();
            }
        } else {
            //source account does not exist
            throw new ServiceException();
        }
    }

    @Override
    @Transactional
    public void deposit(DepositRequest depositRequest) throws ServiceException {
        Long targetAccountId = Long.parseLong(depositRequest.getTargetAccountId().getId());
        Optional<AccountEntity> targetAccountOptional = accountRepository.findById(targetAccountId);
        if (targetAccountOptional.isPresent()) {
            AccountEntity targetAccount = targetAccountOptional.get();
            targetAccount.setCredit(targetAccount.getCredit() + depositRequest.getAmount());
            accountRepository.save(targetAccount);
        } else {
            //target account does not exist
            throw new ServiceException();
        }
    }

    @Override
    @Transactional
    public void withDraw(WithdrawRequest withdrawRequest) throws ServiceException {
        Long targetAccountId = Long.parseLong(withdrawRequest.getTargetAccountId().getId());
        Optional<AccountEntity> targetAccountOptional = accountRepository.findById(targetAccountId);
        if (targetAccountOptional.isPresent()) {
            AccountEntity targetAccount = targetAccountOptional.get();
            if (targetAccount.getCredit() >= withdrawRequest.getAmount()) {
                targetAccount.setCredit(targetAccount.getCredit() - withdrawRequest.getAmount());
                accountRepository.save(targetAccount);
            } else {
                //not enough credit on target account
                throw new ServiceException();
            }
        } else {
            //target account does not exist
            throw new ServiceException();
        }
    }

    @Override
    @Transactional
    public Client getClientInfo(ClientId clientId) throws ServiceException {
        Long id = Long.parseLong(clientId.getId());
        Optional<ClientEntity> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            return DTS.transform(clientOptional.get());
        } else {
            //client not found
            throw new ServiceException();
        }
    }

}
