package com.adp.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.adp.payment.config.PaymentConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(PaymentConfiguration.class)
@ComponentScan("com.adp.payment.*")
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

}
