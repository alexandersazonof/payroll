package by.etc.payroll.service.factory;

import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.AbstractUserService;
import by.etc.payroll.service.impl.ConcreteAdminService;
import by.etc.payroll.service.impl.ConcreteBankAccountService;
import by.etc.payroll.service.impl.ConcreteCardService;
import by.etc.payroll.service.impl.ConcreteUserService;

public class ServiceFactory {
    private static ServiceFactory INSTANCE ;

    private ConcreteUserService userService = new ConcreteUserService();
    private ConcreteBankAccountService bankAccountService = new ConcreteBankAccountService();
    private ConcreteCardService cardService = new ConcreteCardService();
    private ConcreteAdminService adminService = new ConcreteAdminService();

    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceFactory();
        }

        return INSTANCE;
    }

    public ConcreteBankAccountService getBankAccountService () {
        return bankAccountService;
    }


    public ConcreteUserService getUserService() {
        return userService;
    }

    public ConcreteCardService getCardService() {
        return cardService;
    }

    public ConcreteAdminService getAdminService() {
        return adminService;
    }
}
