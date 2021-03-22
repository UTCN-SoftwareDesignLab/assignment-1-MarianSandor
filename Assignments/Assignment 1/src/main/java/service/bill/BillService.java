package service.bill;

import model.Bill;
import view.dto.EmployeeDTO;

import java.util.List;

public interface BillService {

    void addBill(EmployeeDTO employeeDTO);

    List<Bill> getBillsByClient(EmployeeDTO employeeDTO);

    List<Bill> getAllBills();
}
