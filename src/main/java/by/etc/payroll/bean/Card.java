package by.etc.payroll.bean;

import java.io.Serializable;

public class Card implements Serializable {
    private static final long serialVersionUID = 18821428093041L;

    private int id;
    private String number;
    private String date;
    private String customer;
    private int company;
    private int rateId;
    private int money;
    private int idAccount;
    private String valute;

    public Card(int id, String number, String date, String customer, int company, int rate, int idAccount) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.customer = customer;
        this.company = company;
        this.rateId = rate;
        this.idAccount = idAccount;
    }

    public Card() {
    }

    public String getValute() {
        return valute;
    }

    public void setValute(String valute) {
        this.valute = valute;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getRate() {
        return rateId;
    }

    public void setRate(int rate) {
        this.rateId = rate;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (idAccount != card.idAccount) return false;
        if (number != null ? !number.equals(card.number) : card.number != null) return false;
        if (date != null ? !date.equals(card.date) : card.date != null) return false;
        if (customer != null ? !customer.equals(card.customer) : card.customer != null) return false;
        return rateId != card.rateId ;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + rateId;
        result = 31 * result + idAccount;
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", customer='" + customer + '\'' +
                ", company=" + company +
                ", rateId=" + rateId +
                ", money=" + money +
                ", idAccount=" + idAccount +
                ", valute='" + valute + '\'' +
                '}';
    }
}
