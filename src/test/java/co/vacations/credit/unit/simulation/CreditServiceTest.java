package co.vacations.credit.unit.simulation;

import co.vacations.credit.simulation.repository.SimulatorRepository;
import co.vacations.credit.simulation.service.CreditService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CreditServiceTest {

    @Test
    public void getMonthlyPaymentTest() {
        var mockedRepo = mock(SimulatorRepository.class);
        var creditService = new CreditService(mockedRepo);
        var amount = BigDecimal.valueOf(30000000);
        var interest = BigDecimal.valueOf(2);
        var periods = 12;

        var creditDto = creditService.getMonthlyPayment(amount, interest, periods);

        assertThat(creditDto.getMonthlyPayment()).isEqualTo(
                BigDecimal.valueOf(2836787.9).setScale(2, RoundingMode.HALF_UP)
        );
    }

    @Test
    public void getAmortizationTableTest() {
        var mockedRepo = mock(SimulatorRepository.class);
        var creditService = new CreditService(mockedRepo);
        var amount = BigDecimal.valueOf(30000000);
        var interest = BigDecimal.valueOf(2);
        var periods = 12;

        var amortizationTableDto = creditService.getAmortizationTable(amount, interest, periods);

        assertThat(amortizationTableDto.getPayments().size()).isEqualTo(periods);
        assertThat(amortizationTableDto.getAmount()).isEqualTo(amount);
        assertThat(amortizationTableDto.getInterest()).isEqualTo(interest);
    }
}
