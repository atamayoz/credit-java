package co.vacations.credit.simulation.dto;

import java.math.BigDecimal;

public class CreditApplicationDto {
    private BigDecimal amount;
    private BigDecimal interest;
    private Integer periods;
    private BigDecimal monthlyPayment;

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

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal totalAmount) {
        this.monthlyPayment = totalAmount;
    }
}
