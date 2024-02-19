package co.vacations.credit.integration;

import co.vacations.credit.simulation.dto.AmortizationTableDto;
import co.vacations.credit.simulation.dto.CreditApplicationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CreditControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    public void simulateApplicationTest() throws Exception {

        var result = this.mvc.perform(get("/credit/payment/simulator")
                        .queryParam("amount", "30000000")
                        .queryParam("interest", "2")
                        .queryParam("periods", "12")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var resultString = result.getResponse().getContentAsString();
        var creditApplication = this.objectMapper.readValue(resultString, CreditApplicationDto.class);

        assertThat(creditApplication.getMonthlyPayment()).isEqualTo(
                BigDecimal.valueOf(2836787.9).setScale(2, RoundingMode.HALF_UP)
        );
    }

    @Test
    public void getAmortizationTableTest() throws Exception {
        var periods = 12;
        var result = this.mvc.perform(get("/credit/payment/amortization")
                        .queryParam("amount", "30000000")
                        .queryParam("interest", "2")
                        .queryParam("periods", String.valueOf(periods))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var resultString = result.getResponse().getContentAsString();
        var creditApplication = this.objectMapper.readValue(resultString, AmortizationTableDto.class);

        assertThat(creditApplication.getPayments().size()).isEqualTo(periods);
    }
}
