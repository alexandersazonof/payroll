package by.etc.payroll.bean;

import java.io.Serializable;

public class Application implements Serializable {
    private static final long serialVersionUID = 18891428293041L;


    private int id;
    private String action;
    private int accountId;

    public Application() {
    }

    public Application(int id, String action, int accountId) {
        this.id = id;
        this.action = action;
        this.accountId = accountId;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (id != that.id) return false;
        if (accountId != that.accountId) return false;
        return action != null ? action.equals(that.action) : that.action == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + accountId;
        return result;
    }
}
