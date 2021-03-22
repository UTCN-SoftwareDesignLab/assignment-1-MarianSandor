package view.dto;

public class AdministratorDTOBuilder {
    private final AdministratorDTO administratorDTO;

    public AdministratorDTOBuilder() {
        this.administratorDTO = new AdministratorDTO();
    }

    public AdministratorDTOBuilder setId(String id) {
        this.administratorDTO.setId(id);
        return this;
    }

    public AdministratorDTOBuilder setUsername(String username) {
        this.administratorDTO.setUsername(username);
        return this;
    }

    public AdministratorDTOBuilder setPassword(String password) {
        this.administratorDTO.setPassword(password);
        return this;
    }

    public AdministratorDTOBuilder setRole(String role) {
        this.administratorDTO.setRole(role);
        return this;
    }

    public AdministratorDTOBuilder setDateFrom(String dateFrom) {
        this.administratorDTO.setDateFrom(dateFrom);
        return this;
    }

    public AdministratorDTOBuilder setDateTo(String dateTo) {
        this.administratorDTO.setDateTo(dateTo);
        return this;
    }

    public AdministratorDTO build() {
        return this.administratorDTO;
    }
}
