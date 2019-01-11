package by.etc.payroll.service.factory;

import by.etc.payroll.service.AbstractBankAccountService;
import by.etc.payroll.service.AbstractTransactionService;
import by.etc.payroll.service.AbstractUserService;
import by.etc.payroll.service.impl.ConcreteBankAccountService;
import by.etc.payroll.service.impl.ConcreteCardService;
import by.etc.payroll.service.impl.ConcreteTransactionService;
import by.etc.payroll.service.impl.ConcreteUserService;

public class ServiceFactory {
    private static ServiceFactory INSTANCE ;

    private ConcreteUserService userService = new ConcreteUserService();
    private ConcreteBankAccountService bankAccountService = new ConcreteBankAccountService();
    private ConcreteTransactionService transactionService = new ConcreteTransactionService();
    private ConcreteCardService cardService = new ConcreteCardService();

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

    public ConcreteTransactionService getTransactionService() {
        return transactionService;
    }

    public ConcreteUserService getUserService() {
        return userService;
    }

    public ConcreteCardService getCardService() {
        return cardService;
    }
}
