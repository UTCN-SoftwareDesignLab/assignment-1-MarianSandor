package database;

import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String CLIENT = "client";
        public static final String CLIENT_INFO = "client_info";
        public static final String TYPE = "type";
        public static final String USER = "user";
        public static final String BILL = "bill";
        public static final String LOG = "log";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, CLIENT_INFO, TYPE, CLIENT, BILL, LOG};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String CREATE_CLIENT = "create_client";
        public static final String DELETE_CLIENT = "delete_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILL = "process_bill";

        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";
        public static final String VIEW_EMPLOYEE = "view_employee";

        public static final String GENERATE_REPORT = "generate_report";

        public static final String[] RIGHTS = new String[]{ CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT, VIEW_CLIENT,
                                                            TRANSFER_MONEY, PROCESS_BILL,
                                                            CREATE_EMPLOYEE, DELETE_EMPLOYEE, UPDATE_EMPLOYEE, VIEW_EMPLOYEE,
                                                            GENERATE_REPORT
                                                          };
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }

        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_EMPLOYEE, DELETE_EMPLOYEE, UPDATE_EMPLOYEE, VIEW_EMPLOYEE, GENERATE_REPORT));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, TRANSFER_MONEY, PROCESS_BILL));

        return ROLES_RIGHTS;
    }

    public static class Types {
        public static final String CREDIT = "credit";
        public static final String DEBIT = "debit";

        public static final String[] TYPES = new String[]{CREDIT, DEBIT};
    }
}
