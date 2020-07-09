package itx.examples.springbank.common.service;

import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.dto.SystemInfo;

public interface SystemInfoService {

    SystemInfo getSystemInfo() throws ServiceException;

}
