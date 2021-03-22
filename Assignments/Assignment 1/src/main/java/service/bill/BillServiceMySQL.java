package service.bill;

import model.Bill;
import model.builder.BillBuilder;
import repository.bill.BillRepository;
import view.dto.EmployeeDTO;

import java.time.LocalDate;
import java.util.List;

public class BillServiceMySQL implements BillService {
    private final BillRepository billRepository;

    public BillServiceMySQL(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public void addBill(EmployeeDTO employeeDTO) {
        Bill bill = new BillBuilder()
                        .setClientId(Long.valueOf(employeeDTO.getId()))
                        .setTotal(Double.parseDouble(employeeDTO.getBalance()))
                        .setCreationDate(LocalDate.now())
                        .setDetails(employeeDTO.getDetails())
                        .build();

        billRepository.saveBill(bill);
    }

    @Override
    public List<Bill> getBillsByClient(EmployeeDTO employeeDTO) {
        return billRepository.getBillsByClient(Long.valueOf(employeeDTO.getId()));
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.getAllBills();
    }
}
