package model.builder;

import model.Client;

import java.time.LocalDate;


public class ClientBuilder {

    private final Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setUuid(String uuid) {
        client.setUuid(uuid);
        return this;
    }

    public ClientBuilder setBalance(double balance) {
        client.setBalance(balance);
        return this;
    }

    public ClientBuilder setCreationDate(LocalDate date) {
        client.setCreationDate(date);
        return this;
    }

    public ClientBuilder setType(Long type) {
        client.setType(type);
        return this;
    }

    public ClientBuilder setInfo(Long info) {
        client.setInfo(info);
        return this;
    }

    public Client build() {
        return client;
    }
}
