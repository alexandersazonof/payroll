package by.etc.payroll.bean;

import java.io.Serializable;

public class Rate implements Serializable {
    private static final long serialVersionUID = 18821428093041L;

    private int id;
    private String name;
    private String description;

    public Rate() {
    }

    public Rate(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate = (Rate) o;

        if (id != rate.id) return false;
        if (name != null ? !name.equals(rate.name) : rate.name != null) return false;
        return description != null ? description.equals(rate.description) : rate.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
