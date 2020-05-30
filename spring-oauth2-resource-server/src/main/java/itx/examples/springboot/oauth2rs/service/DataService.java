package itx.examples.springboot.oauth2rs.service;

import itx.examples.springboot.oauth2rs.dto.ServerData;

public interface DataService {

    ServerData getData();

    void setData(ServerData serverData);

}
