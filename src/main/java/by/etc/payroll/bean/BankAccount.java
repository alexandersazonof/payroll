package by.etc.payroll.bean;

import java.io.Serializable;
import java.util.List;

public class BankAccount implements Serializable {

    private static final long serialVersionUID = 18891428293041L;

    private int id;
    private String name;
    private String number;
    private boolean status;
    private int countOfMoney;
    private int userId;
    private String valute;
    private List<Card> cardList;

    public BankAccount() {
    }

    public BankAccount(int id, String name, String number, boolean status, int countOfMoney, int userId) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.status = status;
        this.countOfMoney = countOfMoney;
        this.userId = userId;
    }

    public String getValute() {
        return valute;
    }

    public void setValute(String valute) {
        this.valute = valute;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCountOfMoney() {
        return countOfMoney;
    }

    public void setCountOfMoney(int countOfMoney) {
        this.countOfMoney = countOfMoney;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccount that = (BankAccount) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (countOfMoney != that.countOfMoney) return false;
        if (userId != that.userId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + countOfMoney;
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", status=" + status +
                ", countOfMoney=" + countOfMoney +
                ", userId=" + userId +
                '}';
    }
}
