package by.etc.payroll.bean;

import java.io.Serializable;
import java.util.List;

public class Cource implements Serializable {
    private static final long serialVersionUID = 18821428093041L;
    private String nameValute;
    private int exchangeId;
    private List<ExchangeRate> listExchenge;

    public Cource(String nameValute, int exchangeId, List<ExchangeRate> listExchenge) {
        this.nameValute = nameValute;
        this.exchangeId = exchangeId;
        this.listExchenge = listExchenge;
    }

    public Cource() {
    }

    public String getNameValute() {
        return nameValute;
    }

    public void setNameValute(String nameValute) {
        this.nameValute = nameValute;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public List<ExchangeRate> getListExchenge() {
        return listExchenge;
    }

    public void setListExchenge(List<ExchangeRate> listExchenge) {
        this.listExchenge = listExchenge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cource cource = (Cource) o;

        if (exchangeId != cource.exchangeId) return false;
        if (nameValute != null ? !nameValute.equals(cource.nameValute) : cource.nameValute != null) return false;
        return listExchenge != null ? listExchenge.equals(cource.listExchenge) : cource.listExchenge == null;
    }

    @Override
    public int hashCode() {
        int result = nameValute != null ? nameValute.hashCode() : 0;
        result = 31 * result + exchangeId;
        result = 31 * result + (listExchenge != null ? listExchenge.hashCode() : 0);
        return result;
    }
}
