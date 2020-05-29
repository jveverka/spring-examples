package itx.examples.springbank.server.service;

import itx.examples.springbank.common.dto.LedgerRecord;
import itx.examples.springbank.common.dto.ServiceException;

import java.util.Collection;

public interface LedgerService {

    void saveRecord(LedgerRecord ledgerRecord) throws ServiceException;

    Collection<LedgerRecord> getLedger();

}
