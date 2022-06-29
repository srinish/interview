package com.adp.payment.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adp.payment.config.PaymentConfiguration;
import com.adp.payment.service.PaymentService;


@RestController
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	
	@Autowired
	PaymentService service;

	@Autowired
	PaymentConfiguration paymentConfig;
	
	@RequestMapping(path="/greet")
	public String greetMe() {

		return "Hello Mala!" ;
	}
	
	@GetMapping(path="/change/{bill}")
	public  Map<Integer,Integer> changeBill(@PathVariable("bill") @Size(min = 1) Integer bill) {

			Map<Integer,Integer> changeDue = service.getChangeDue(bill);
			return  changeDue;

	  }
}
