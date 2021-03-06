package by.etc.payroll.service.creator;

import by.etc.payroll.bean.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class Creator {

    private final static int BEGIN_NUMBER = 10000;
    private final static int END_NUMBER = 999999999;

    private final static long RANDOM_CARD_BEGIN_NUMBER = 10000000;
    private final static long RANDOM_CARD_END_NUMBER = 99999999;
    private final static String CODE = "BY";

    private final static int CARD_TERM_YEAR = 2;

    private final static String FORMAT = "yyyy-MM-dd";

    public static List<UserWithBankAccount> takeUserWithBankAccount (List<User> userList, List<BankAccount> bankAccountList) {
        List<UserWithBankAccount> userWithBankAccountList = new ArrayList<>();

        for (User user :userList) {
            UserWithBankAccount userWithBankAccount = new UserWithBankAccount(user);
            List<BankAccount> tempBankAccount = new ArrayList<>();

            for (BankAccount bankAccount :bankAccountList) {
                if (user.getId() == bankAccount.getUserId()) {
                    int totalMoney = bankAccount.getCountOfMoney();

                    if (bankAccount.getCardList() != null) {
                        for (Card card :bankAccount.getCardList()) {
                            totalMoney += card.getMoney();
                        }
                    }


                    bankAccount.setCountOfMoney(totalMoney);
                    tempBankAccount.add(bankAccount);
                }
            }
            userWithBankAccount.setBankAccountList(tempBankAccount);

            userWithBankAccountList.add(userWithBankAccount);
        }

        return userWithBankAccountList;
    }

    public static Rate takeRate (String name, String description) {
        Rate rate = new Rate();
        rate.setName(name);
        rate.setDescription(description);
        return rate;
    }

    public static User takeUserWithoutPassword (int id, String login, String lastName, String firstName, String role, String email) {
        User user = new User();
        user.setId(id);
        user.setRole(role);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);

        return user;
    }

    public static Application takeApplication (String action, int accountId) {
        Application application = new Application();
        application.setAction(action);
        application.setAccountId(accountId);
        return application;
    }

    public static ExchangeRate takeExchangeRate (int id, int fromValuteId, int toValiteId, float course) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(id);
        exchangeRate.setFromValuteId(fromValuteId);
        exchangeRate.setToValuteId(toValiteId);
        exchangeRate.setCourse(course);

        return exchangeRate;
    }

    public static List<Cource> takeCourceList (List<Valute> valuteList, List<ExchangeRate> exchangeRateList) {
        List<Cource> courceList = new ArrayList<>();

        for (Valute valute :valuteList) {
            Cource cource = new Cource();
            int valuteId  = valute.getId();
            List<ExchangeRate> listChange = new ArrayList<>();

            for (ExchangeRate e :exchangeRateList) {
                if (e.getFromValuteId() == valuteId) {
                    listChange.add(e);
                }
            }
            cource.setNameValute(valute.getName());
            cource.setListExchenge(listChange);
            courceList.add(cource);
        }

        return courceList;
    }

    public static BankAccount takeBankAccount (String name, String number, boolean status, int userId, int money,
                                               String valute) {
        BankAccount bankAccount = new BankAccount();

        bankAccount.setName(name);
        bankAccount.setNumber(number);
        bankAccount.setStatus(status);
        bankAccount.setUserId(userId);
        bankAccount.setCountOfMoney(money);
        bankAccount.setValute(valute);
        return bankAccount;
    }

    public static BankAccount takeBankAccount (int id, String name, String number, boolean status, int userId, int money,
                                               String valute) {
        BankAccount bankAccount = new BankAccount();


        bankAccount.setId(id);
        bankAccount.setName(name);
        bankAccount.setNumber(number);
        bankAccount.setStatus(status);
        bankAccount.setUserId(userId);
        bankAccount.setCountOfMoney(money);
        bankAccount.setValute(valute);
        return bankAccount;
    }

    public static String takeNumberOfBankAccount() {
        return CODE + String.valueOf(BEGIN_NUMBER + (int)(Math.random()  * END_NUMBER));
    }

    public static String takeNumberOfCards() {
        return String.valueOf(RANDOM_CARD_BEGIN_NUMBER + (int)(Math.random()  * RANDOM_CARD_END_NUMBER)) + String.valueOf(RANDOM_CARD_BEGIN_NUMBER + (int)(Math.random()  * RANDOM_CARD_END_NUMBER));
    }

    public static Operation takeOperation (String action, String number, int id) {
        Operation operation = new Operation();

        operation.setAction(action);
        operation.setNumber(number);
        operation.setUserId(id);
        SimpleDateFormat dmyFormat = new SimpleDateFormat(FORMAT);
        operation.setDate(dmyFormat.format(new Date()));

        return operation;
    }

    public static Operation takeOperation (int id, String action, String number,String date, int userId) {
        Operation operation = new Operation();

        operation.setId(id);
        operation.setAction(action);
        operation.setNumber(number);
        operation.setUserId(userId);
        operation.setDate(date);

        return operation;
    }

    public static Card takeCard (String number, String date, String customer, int companyId, int accountId, int rateId, int money, String valute) {
        Card card = new Card();
        card.setNumber(number);
        card.setDate(date);
        card.setCustomer(customer);
        card.setCompany(companyId);
        card.setIdAccount(accountId);
        card.setRate(rateId);
        card.setMoney(money);
        card.setValute(valute);

        return card;
    }
    public static Card takeCard (int id, String number, String date, String customer, int companyId, int accountId, int rateId, int money, String valute) {
        Card card = new Card();
        card.setId(id);
        card.setNumber(number);
        card.setDate(date);
        card.setCustomer(customer);
        card.setCompany(companyId);
        card.setIdAccount(accountId);
        card.setRate(rateId);
        card.setMoney(money);
        card.setValute(valute);

        return card;
    }

    public static String getCustomer (String firstName, String lastName) {
        return lastName.toUpperCase() + " " + firstName.toUpperCase();
    }

    public static String getDateForCard() {
        SimpleDateFormat myFormat = new SimpleDateFormat(FORMAT);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR) + CARD_TERM_YEAR;

        Calendar calendar = new GregorianCalendar(year, month, day);
        return myFormat.format(calendar.getTime());

    }
    public static UserData takeUserData (String firstName, String lastName, String address, String city, int idCard) {
        UserData userData = new UserData();
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setAddress(address);
        userData.setCity(city);
        userData.setIdCard(idCard);
        return userData;
    }


    public static Transfer takeTransfer (int id, int fromCardId, int toCardId, int money) {
        Transfer transfer = new Transfer();
        transfer.setId(id);
        transfer.setFromCardId(fromCardId);
        transfer.setToCardId(toCardId);
        transfer.setMoney(money);
        return transfer;
    }

    public static Transfer takeTransfer (int fromCardId, int toCardId, int money) {
        Transfer transfer = new Transfer();
        transfer.setFromCardId(fromCardId);
        transfer.setToCardId(toCardId);
        transfer.setMoney(money);
        return transfer;
    }

    public static Valute takeValute (int id, String name) {
        return new Valute (id, name);
    }

    public static Rate takeRate (int id, String name, String description) {
        return new Rate(id, name, description);
    }

    public static Company takeCompany (int id, String name) { return new Company(id, name); }
}
