package model.builder;

import model.ClientInfo;
import model.User;

public class ClientInfoBuilder {

    private final ClientInfo clientInfo;

    public ClientInfoBuilder() {
        clientInfo = new ClientInfo();
    }

    public ClientInfoBuilder setId(Long id) {
        clientInfo.setId(id);
        return this;
    }

    public ClientInfoBuilder setName(String name) {
        clientInfo.setName(name);
        return this;
    }

    public ClientInfoBuilder setIcn(String icn) {
        clientInfo.setIcn(icn);
        return this;
    }

    public ClientInfoBuilder setPnc(String pnc) {
        clientInfo.setPnc(pnc);
        return this;
    }

    public ClientInfoBuilder setAddress(String address) {
        clientInfo.setAddress(address);
        return this;
    }

    public ClientInfo build() {
        return clientInfo;
    }
}
