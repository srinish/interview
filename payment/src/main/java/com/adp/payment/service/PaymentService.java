package com.adp.payment.service;

import java.math.BigDecimal;
import java.util.Map;

import com.adp.payment.exception.InvalidBillAmountException;
import com.adp.payment.exception.NotEnoughCoinsException;


public interface PaymentService {
	
	public Map<Integer, Integer> getChangeDue(Integer bill) throws InvalidBillAmountException, NotEnoughCoinsException;

}
