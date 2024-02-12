package co.vacations.credit.application.service;

import co.vacations.credit.application.dto.CreditApplicationDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class CreditService {

    private static final int ONE_HUNDRED = 100;
    public CreditApplicationDto getMonthlyPayment(
            BigDecimal amount, BigDecimal interest, Integer periods
    ) {
        // Here I calculate the PMT periodic payment (annuity payment).
        // PMT = (PV * r * (1 + r)^n) / ((1 + r)^n - 1)
        var dividedInterest = interest.divide(BigDecimal.valueOf(ONE_HUNDRED), 4, RoundingMode.HALF_UP);
        var convertedInterest = BigDecimal.ONE.add(dividedInterest).pow(periods).setScale(4, RoundingMode.HALF_UP);

        var pmt = amount.multiply(dividedInterest).multiply(convertedInterest)
                .divide(convertedInterest.subtract(BigDecimal.ONE), 4, RoundingMode.HALF_UP);

        var application = new CreditApplicationDto();
        application.setAmount(amount);
        application.setInterest(interest);
        application.setPeriods(periods);
        application.setMonthlyPayment(pmt);

        return application;
    }
}
