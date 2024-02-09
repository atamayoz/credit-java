package co.vacations.credit.application.service;

import co.vacations.credit.application.dto.CreditApplicationDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class ApplicationService {

    private static final int ONE_HUNDRED = 100;
    public CreditApplicationDto getApplication(
            BigDecimal amount, BigDecimal interest, Integer periods
    ) {
        // Here I calculate the future value of an amount in a period of time and interest rate
        // FV=PV×(1+r)^n
        var convertedInterest = interest.divide(BigDecimal.valueOf(ONE_HUNDRED), 4, RoundingMode.HALF_UP);
        var totalAmount = amount.multiply(
                BigDecimal.ONE.add(convertedInterest).pow(periods, MathContext.DECIMAL32)
        ).setScale(2, RoundingMode.HALF_UP);

        var application = new CreditApplicationDto();
        application.setAmount(amount);
        application.setInterest(interest);
        application.setPeriods(periods);
        application.setTotalAmount(totalAmount);

        return application;
    }
}
