package by.etc.payroll.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 18821428093041L;

    private int id;
    private String fromNumber;
    private String toNumber;
    private int count;
    private String date;



    public Transaction() {
    }

    public Transaction(int id, String fromNumber, String toNumber, int count, String date) {
        this.id = id;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.count = count;
        this.date = date;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (count != that.count) return false;
        if (fromNumber != null ? !fromNumber.equals(that.fromNumber) : that.fromNumber != null) return false;
        if (toNumber != null ? !toNumber.equals(that.toNumber) : that.toNumber != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fromNumber != null ? fromNumber.hashCode() : 0);
        result = 31 * result + (toNumber != null ? toNumber.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", fromNumber='" + fromNumber + '\'' +
                ", toNumber='" + toNumber + '\'' +
                ", count=" + count +
                ", date='" + date + '\'' +
                '}';
    }
}
