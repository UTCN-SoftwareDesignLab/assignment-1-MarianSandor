package view.dto;

public class EmployeeDTOBuilder {
    private EmployeeDTO employeeDTO;

    public EmployeeDTOBuilder() {
        this.employeeDTO = new EmployeeDTO();
    }

    public EmployeeDTOBuilder setId(String id) {
        employeeDTO.setId(id);
        return this;
    }

    public EmployeeDTOBuilder setIdFrom(String idFrom) {
        employeeDTO.setIdFrom(idFrom);
        return this;
    }

    public EmployeeDTOBuilder setIdTo(String idTo) {
        employeeDTO.setIdTo(idTo);
        return this;
    }

    public EmployeeDTOBuilder setBalance(String balance) {
        employeeDTO.setBalance(balance);
        return this;
    }

    public EmployeeDTOBuilder setUuid(String uuid) {
        employeeDTO.setUuid(uuid);
        return this;
    }

    public EmployeeDTOBuilder setType(String type) {
        employeeDTO.setType(type);
        return this;
    }

    public EmployeeDTOBuilder setName(String name) {
        employeeDTO.setName(name);
        return this;
    }

    public EmployeeDTOBuilder setIcn(String icn) {
        employeeDTO.setIcn(icn);
        return this;
    }

    public EmployeeDTOBuilder setPnc(String pnc) {
        employeeDTO.setPnc(pnc);
        return this;
    }

    public EmployeeDTOBuilder setAddress(String address) {
        employeeDTO.setAddress(address);
        return this;
    }

    public EmployeeDTOBuilder setDetails(String details) {
        employeeDTO.setDetails(details);
        return this;
    }

    public EmployeeDTO build() {
        return employeeDTO;
    }
}
