package by.etc.payroll.bean;

import java.io.Serializable;

public class ExchangeRate implements Serializable {
    private static final long serialVersionUID = 18821428093041L;

    private int id;
    private int fromValuteId;
    private int toValuteId;
    private float course;

    public ExchangeRate() {
    }

    public ExchangeRate(int id, int fromValuteId, int toValuteId, float course) {
        this.id = id;
        this.fromValuteId = fromValuteId;
        this.toValuteId = toValuteId;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromValuteId() {
        return fromValuteId;
    }

    public void setFromValuteId(int fromValuteId) {
        this.fromValuteId = fromValuteId;
    }

    public int getToValuteId() {
        return toValuteId;
    }

    public void setToValuteId(int toValuteId) {
        this.toValuteId = toValuteId;
    }

    public float getCourse() {
        return course;
    }

    public void setCourse(float course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeRate that = (ExchangeRate) o;

        if (id != that.id) return false;
        if (fromValuteId != that.fromValuteId) return false;
        if (toValuteId != that.toValuteId) return false;
        return Float.compare(that.course, course) == 0;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + fromValuteId;
        result = 31 * result + toValuteId;
        result = 31 * result + (course != +0.0f ? Float.floatToIntBits(course) : 0);
        return result;
    }
}

