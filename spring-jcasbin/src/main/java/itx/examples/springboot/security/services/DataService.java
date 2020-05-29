package itx.examples.springboot.security.services;

import itx.examples.springboot.security.services.dto.ServerData;

public interface DataService {

    public void setState(String state);

    ServerData getSecuredData(String source);

}
