package co.vacations.credit.simulation.service;

import co.vacations.credit.simulation.dto.AmortizationRow;
import co.vacations.credit.simulation.dto.AmortizationTableDto;
import co.vacations.credit.simulation.dto.CreditApplicationDto;
import co.vacations.credit.simulation.entity.SimulatorEntity;
import co.vacations.credit.simulation.repository.SimulatorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CreditService {

    private static final int ONE_HUNDRED = 100;
    private final SimulatorRepository simulatorRepository;
    public CreditService(SimulatorRepository simulatorRepository) {
        this.simulatorRepository = simulatorRepository;
    }

    @Transactional
    public CreditApplicationDto getMonthlyPayment(
            BigDecimal amount, BigDecimal interest, Integer periods
    ) {
        // Here I calculate the PMT periodic payment (annuity payment).
        // PMT = (PV * r * (1 + r)^n) / ((1 + r)^n - 1)
        var dividedInterest = interest.divide(BigDecimal.valueOf(ONE_HUNDRED), 9, RoundingMode.HALF_UP);
        var pow = BigDecimal.ONE.add(dividedInterest).pow(periods).setScale(9, RoundingMode.HALF_UP);

        var pmt = amount.multiply(dividedInterest).multiply(pow)
                .divide(pow.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        // Save the pmt in the DB
        var simulatorEntity = new SimulatorEntity();
        simulatorEntity.setAmount(amount);
        simulatorEntity.setInterest(pow);
        simulatorEntity.setPeriods(periods);
        simulatorEntity.setMonthlyPayment(pmt);

        simulatorRepository.save(simulatorEntity);

        var application = new CreditApplicationDto();
        application.setAmount(amount);
        application.setInterest(interest);
        application.setPeriods(periods);
        application.setMonthlyPayment(pmt);

        return application;
    }


    public AmortizationTableDto getAmortizationTable(
            BigDecimal amount, BigDecimal interest, Integer periods
    ) {
        var payment = getMonthlyPayment(amount, interest, periods).getMonthlyPayment();

        final BigDecimal[] remainingBalance = {amount};
        var convertedInterest = interest.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);


        var payments = IntStream.rangeClosed(1, periods).mapToObj(period -> {
            var interestAmount = remainingBalance[0].multiply(convertedInterest).setScale(2, RoundingMode.HALF_UP);
            var principal = payment.subtract(interestAmount).setScale(2, RoundingMode.HALF_UP);
            remainingBalance[0] = remainingBalance[0].subtract(principal).setScale(2, RoundingMode.HALF_UP);

            if (remainingBalance[0].compareTo(BigDecimal.ZERO) < 0) {
                interestAmount = interestAmount.add(remainingBalance[0]);
                remainingBalance[0] = BigDecimal.ZERO;
            }

            var amortization = new AmortizationRow();
            amortization.setInstallment(period);
            amortization.setPaymentAmount(payment);
            amortization.setInterestAmount(interestAmount);
            amortization.setPrincipal(principal);
            amortization.setRemainingAmount(remainingBalance[0]);

            return amortization;
        }).collect(Collectors.toList());

        var amortizationTable = new AmortizationTableDto();
        amortizationTable.setInterest(interest);
        amortizationTable.setAmount(amount);
        amortizationTable.setPeriods(periods);
        amortizationTable.setPayments(payments);

        return amortizationTable;
    }
}
