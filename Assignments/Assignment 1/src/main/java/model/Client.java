package model;

import java.time.LocalDate;

public class Client {

    private Long id;
    private String uuid;
    private double balance;
    private LocalDate creationDate;
    private Long type;
    private Long info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getInfo() {
        return info;
    }

    public void setInfo(Long info) {
        this.info = info;
    }

}
