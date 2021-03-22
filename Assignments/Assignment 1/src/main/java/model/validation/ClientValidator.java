package model.validation;

import model.Client;
import model.ClientInfo;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private static final int PNC_LENGTH = 13;
    private static final int ICN_LENGTH = 16;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final ClientInfo clientInfo;
    private final Client client;

    public ClientValidator(Client client, ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateICN(clientInfo.getIcn());
        validatePNC(clientInfo.getPnc());

        return errors.isEmpty();
    }

    public void validateICN(String icn) {
        if (icn.length() != ICN_LENGTH) {
            errors.add("ICN should contain exactly " + ICN_LENGTH + " characters!");
        }

        if (!constainsOnlyDigits(icn)) {
            errors.add("ICN must contain only digits!");
        }
    }

    public void validatePNC(String pnc) {
        if (pnc.length() != ICN_LENGTH) {
            errors.add("PNC should contain exactly " + PNC_LENGTH + " characters!");
        }

        if (!constainsOnlyDigits(pnc)) {
            errors.add("PNC must contain only digits!");
        }
    }

    private boolean constainsOnlyDigits(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

}
