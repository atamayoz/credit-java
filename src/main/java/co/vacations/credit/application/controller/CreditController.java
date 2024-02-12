package co.vacations.credit.application.controller;


import co.vacations.credit.application.dto.CreditApplicationDto;
import co.vacations.credit.application.service.CreditService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/credit")
public class CreditController {
    private final CreditService service;

    public CreditController(CreditService service) {
        this.service = service;
    }

    @GetMapping("/payment/calculator")
    public ResponseEntity<CreditApplicationDto> simulateApplication(
            @RequestParam("amount")
            @Positive(message = "The amount must be greater than zero")
            BigDecimal amount,
            @RequestParam("interest")
            @Positive(message = "The interest must be greater than zero")
            BigDecimal interest,
            @RequestParam("periods")
            @Positive(message = "The number of periods must be greater than zero")
            Integer periods
    ) {
        return ResponseEntity.ok(this.service.getMonthlyPayment(amount, interest, periods));
    }
}
