package itx.examples.springboot.security.springsecurity.jwt.services;

import itx.examples.springboot.security.springsecurity.jwt.services.dto.ServerData;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {

    @Override
    public ServerData getSecuredData(String source) {
        return new ServerData(source, "Some Data", System.currentTimeMillis());
    }

}
