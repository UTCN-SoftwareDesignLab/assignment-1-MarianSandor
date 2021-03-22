package repository.bill;

import model.Bill;

import java.util.List;

public interface BillRepository {

    void saveBill(Bill bill);

    List<Bill> getAllBills();

    List<Bill> getBillsByClient(Long clientId);

    void removeAll();
}
