package itx.examples.springbank.server.service;

import itx.examples.springbank.common.dto.LedgerRecord;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.server.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;

    @Autowired
    public LedgerServiceImpl(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public void saveRecord(LedgerRecord ledgerRecord) throws ServiceException {

    }

    @Override
    public Collection<LedgerRecord> getLedger() {
        return null;
    }

}
