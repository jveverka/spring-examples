package itx.examples.springboot.oauth2rs.service;

import itx.examples.springboot.oauth2rs.dto.ServerData;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {

    private ServerData serverData;

    public DataServiceImpl() {
        this.serverData = new ServerData("data");
    }

    @Override
    public ServerData getData() {
        return this.serverData;
    }

    @Override
    public void setData(ServerData serverData) {
        this.serverData = serverData;
    }

}
