package co.vacations.credit.simulation.dto;

import java.math.BigDecimal;
import java.util.List;

public class AmortizationTableDto {
    private BigDecimal amount;
    private BigDecimal interest;
    private int periods;
    private List<AmortizationRow> payments;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public List<AmortizationRow> getPayments() {
        return payments;
    }

    public void setPayments(List<AmortizationRow> payments) {
        this.payments = payments;
    }
}
