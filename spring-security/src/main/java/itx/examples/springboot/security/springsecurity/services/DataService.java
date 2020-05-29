package itx.examples.springboot.security.springsecurity.services;

import itx.examples.springboot.security.springsecurity.services.dto.ServerData;

public interface DataService {

    ServerData getSecuredData(String source);

}
