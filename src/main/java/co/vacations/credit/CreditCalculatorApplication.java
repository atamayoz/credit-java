package co.vacations.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CreditCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCalculatorApplication.class, args);
	}

}
