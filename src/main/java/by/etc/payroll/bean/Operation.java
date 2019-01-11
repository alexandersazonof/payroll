package by.etc.payroll.bean;

import java.io.Serializable;

public class Operation implements Serializable {
    private static final long serialVersionUID = 13821428093041L;

    private int id;
    private String action;
    private String date;
    private String number;
    private int userId;

    public Operation() {
    }

    public Operation(int id, String action, String date, String number, int userId) {
        this.id = id;
        this.action = action;
        this.date = date;
        this.number = number;
        this.userId = userId;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

        Operation operation = (Operation) o;

        if (id != operation.id) return false;
        if (userId != operation.userId) return false;
        if (action != null ? !action.equals(operation.action) : operation.action != null) return false;
        if (date != null ? !date.equals(operation.date) : operation.date != null) return false;
        return number != null ? number.equals(operation.number) : operation.number == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", date='" + date + '\'' +
                ", number='" + number + '\'' +
                ", userId=" + userId +
                '}';
    }
}
