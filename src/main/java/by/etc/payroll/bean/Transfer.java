package by.etc.payroll.bean;

import java.io.Serializable;

public class Transfer implements Serializable {
    private final static long serialVersionUID = 18821428093041L;

    private int id;
    private int fromCardId;
    private int toCardId;
    private int money;

    public Transfer() {
    }

    public Transfer(int id, int fromCardId, int toCardId, int money) {
        this.id = id;
        this.fromCardId = fromCardId;
        this.toCardId = toCardId;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromCardId() {
        return fromCardId;
    }

    public void setFromCardId(int fromCardId) {
        this.fromCardId = fromCardId;
    }

    public int getToCardId() {
        return toCardId;
    }

    public void setToCardId(int toCardId) {
        this.toCardId = toCardId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfer = (Transfer) o;

        if (id != transfer.id) return false;
        if (fromCardId != transfer.fromCardId) return false;
        if (toCardId != transfer.toCardId) return false;
        return money == transfer.money;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + fromCardId;
        result = 31 * result + toCardId;
        result = 31 * result + money;
        return result;
    }
}
