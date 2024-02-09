package co.vacations.credit.application.controller;


import co.vacations.credit.application.dto.CreditApplicationDto;
import co.vacations.credit.application.service.ApplicationService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/credit")
public class CreditApplicationController {
    private final ApplicationService service;

    public CreditApplicationController(ApplicationService service) {
        this.service = service;
    }
    @GetMapping("/application")
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
        return ResponseEntity.ok(this.service.getApplication(amount, interest, periods));
    }
}
