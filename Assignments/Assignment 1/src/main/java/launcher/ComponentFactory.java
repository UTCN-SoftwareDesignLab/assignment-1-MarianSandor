package launcher;

import controller.AdministratorController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.bill.BillRepository;
import repository.bill.BillRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.clientInfo.ClientInfoRepository;
import repository.clientInfo.ClientInfoRepositoryMySQL;
import repository.log.LogRepository;
import repository.log.LogRepositoryMySQL;
import repository.roles.RightsRolesRepository;
import repository.roles.RightsRolesRepositoryMySQL;
import repository.type.TypeRepository;
import repository.type.TypeRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.bill.BillService;
import service.bill.BillServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.authentication.AuthenticationService;
import service.authentication.AuthenticationServiceMySQL;
import service.log.LogService;
import service.log.LogServiceMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;
import view.administartor.AdministratorView;
import view.employee.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdministratorView administratorView;

    private final LoginController loginController;
    private final EmployeeController employeeController;
    private final AdministratorController administratorController;

    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final BillService billService;
    private final UserService userService;
    private final LogService logService;

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientInfoRepository clientInfoRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final TypeRepository typeRepository;
    private final BillRepository billRepository;
    private final LogRepository logRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.clientInfoRepository = new ClientInfoRepositoryMySQL(connection);
        this.clientRepository = new ClientRepositoryMySQL(connection, this.clientInfoRepository);
        this.typeRepository = new TypeRepositoryMySQL(connection);
        this.billRepository = new BillRepositoryMySQL(connection);
        this.logRepository = new LogRepositoryMySQL(connection);

        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.clientService = new ClientServiceMySQL(this.clientRepository, this.clientInfoRepository, typeRepository);
        this.billService = new BillServiceMySQL(billRepository);
        this.userService = new UserServiceMySQL(userRepository, rightsRolesRepository);
        this.logService = new LogServiceMySQL(logRepository, userRepository);

        this.loginView = new LoginView();
        this.employeeView = new EmployeeView();
        this.administratorView = new AdministratorView();

        this.loginController = new LoginController(loginView, employeeView, administratorView, authenticationService);
        this.employeeController = new EmployeeController(employeeView, loginView, clientService, billService, logService);
        this.administratorController = new AdministratorController(administratorView, loginView, userService, logService);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public AdministratorView getAdministratorView() { return administratorView; }

    public LoginController getLoginController() {
        return loginController;
    }
}
