package model.builder;

import model.Bill;

import java.time.LocalDate;

public class BillBuilder {

    private final Bill bill;

    public BillBuilder() {
        this.bill = new Bill();
    }

    public BillBuilder setId(Long id) {
        bill.setId(id);
        return this;
    }

    public BillBuilder setClientId(Long clientId) {
        bill.setClientId(clientId);
        return this;
    }

    public BillBuilder setTotal(double total) {
        bill.setTotal(total);
        return this;
    }

    public BillBuilder setCreationDate(LocalDate date) {
        bill.setCreationDate(date);
        return this;
    }

    public BillBuilder setDetails(String details) {
        bill.setDetails(details);
        return this;
    }

    public Bill build() {
        return this.bill;
    }
}
