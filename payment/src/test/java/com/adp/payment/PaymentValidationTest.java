package com.adp.payment;


import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adp.payment.config.PaymentConfiguration;
import com.adp.payment.exception.InvalidBillAmountException;
import com.adp.payment.exception.NotEnoughCoinsException;
import com.adp.payment.service.impl.PaymentServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentValidationTest {
	

	@Autowired
	private PaymentServiceImpl service;
	

	@Test
	@Order(1)
	public void testChangeDue_success() throws Exception {

		Map<Integer,Integer> coinMap = new HashMap<>();
		coinMap.put(1, 1);
		coinMap.put(10, 1);

		Map<Integer,Integer> result = service.getChangeDue(11);
		Assert.assertNotNull(result);
	     
	}
	
	
	@Test(expected=InvalidBillAmountException.class)
	@Order(2)
	public void testChangeDue_nullBillAmount() throws Exception {
		
		Map<Integer,Integer> coinMap = new HashMap<>();
		coinMap.put(1, 1);
		coinMap.put(10, 1);

		Map<Integer,Integer> result = service.getChangeDue(null);

	}
	
	@Test(expected=NotEnoughCoinsException.class)
	@Order(3)
	public void testChangeDue_billAmtExceedsAvailCoins() throws Exception {
		
		Map<Integer,Integer> result = service.getChangeDue(100);

	}
	
	@Test(expected=NotEnoughCoinsException.class)
	@Order(4)
	public void testChangeDue_notEnoughCoinsAvail() throws Exception {

		Map<Integer,Integer> result = service.getChangeDue(100);
		result = service.getChangeDue(10);

	}
}