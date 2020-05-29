package itx.examples.springbank.common.service;

import itx.examples.springbank.common.dto.Client;
import itx.examples.springbank.common.dto.ClientId;
import itx.examples.springbank.common.dto.DepositRequest;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.TransactionRequest;
import itx.examples.springbank.common.dto.WithdrawRequest;

public interface BankService {

    void transferFunds(TransactionRequest transactionRequest) throws ServiceException;

    void deposit(DepositRequest depositRequest) throws ServiceException;

    void withDraw(WithdrawRequest withdrawRequest) throws ServiceException;

    Client getClientInfo(ClientId clientId) throws ServiceException;

}
