package com.adp.payment;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

import com.adp.payment.service.PaymentService;
import com.adp.payment.service.impl.PaymentServiceImpl;

@Profile("test")
@Configuration
@TestPropertySource(locations="classpath:test.properties")
public class PaymentServiceTestConfiguration {

   @Bean
   @Primary
   public PaymentService productService() {
      return Mockito.mock(PaymentServiceImpl.class);
   }
}