package by.etc.payroll.bean;

import java.util.List;

public class UserWithBankAccount extends User {
    private List<BankAccount> bankAccountList;

    public UserWithBankAccount(int id, String login, String password, String lastName, String firstName, String role, String email, List<BankAccount> bankAccountList) {
        super(id, login, password, lastName, firstName, role, email);
        this.bankAccountList = bankAccountList;
    }

    public UserWithBankAccount(){}

    public UserWithBankAccount (User user) {
        super(user.getId(),user.getLogin(), user.getLastName(), user.getFirstName(), user.getRole(), user.getEmail());
    }
    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }


}
