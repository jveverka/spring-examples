package itx.examples.springbank.server.service;

import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.SystemInfo;
import itx.examples.springbank.common.service.SystemInfoService;
import org.springframework.stereotype.Service;

@Service
public class SystemInfoServiceImpl implements SystemInfoService {

    @Override
    public SystemInfo getSystemInfo() throws ServiceException {
        return new SystemInfo("1.0.0");
    }

}
