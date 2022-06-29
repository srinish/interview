package com.adp.payment.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.adp.payment.config.PaymentConfiguration;
import com.adp.payment.exception.InvalidBillAmountException;
import com.adp.payment.exception.NotEnoughCoinsException;
import com.adp.payment.service.PaymentService;

import com.adp.payment.util.PaymentUtil;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentConfiguration paymentConfig;
	
	@Autowired
	Map<Integer,Integer> coinBalanceMap;
	
	@Override
	public Map<Integer, Integer> getChangeDue(Integer bill) {
		Map<Integer, Integer> changeDue = new HashMap<>();
		List<Integer> coinsList = paymentConfig.getAvailableCoins();
	    Integer totalCoinsValue =  getTotalValueOfAvailableCoins() ;
	    System.out.println("Total value of coins : " + totalCoinsValue);

	    //bill *= 100; //convert bill to cents;
	    System.out.println("Bill value : " + bill);	    
	    if (bill > totalCoinsValue )
	    	throw new InvalidBillAmountException("Bill amount exceeds the total available coins");
	    coinsList = coinBalanceMap.entrySet().stream().filter(x -> x.getValue() != 0).map(Map.Entry::getKey).collect(Collectors.toList());
	    System.out.println("Coins Available " + Arrays.asList(coinsList));
	    List<Integer> minCoinsList = PaymentUtil.countMinCoinsUtil(bill, coinsList, coinsList.size());
		System.out.println("Coins to be used " + Arrays.asList(minCoinsList));
		for(int i=0; i<minCoinsList.size(); i++) {
			int count = 0;
			if (changeDue.containsKey(minCoinsList.get(i))) {
				count = changeDue.get(minCoinsList.get(i));
			}
			changeDue.put(minCoinsList.get(i), ++count);
		}
		updateBalanceCoins(changeDue);

		return changeDue;
	}
	
	public void updateBalanceCoins(Map<Integer, Integer> changeDue) throws NotEnoughCoinsException {
		changeDue.entrySet().forEach(System.out::println);
		
		for(Map.Entry<Integer,Integer> entry : changeDue.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();

			Integer currentBalance = coinBalanceMap.get(key);
			System.out.println("Key " + key + " Value " + value + " Current Balance " + currentBalance);
			if (currentBalance < value)
				throw new NotEnoughCoinsException("Required Coins does not exists");
			else
				coinBalanceMap.put(key, (currentBalance-value));
		}
		
		coinBalanceMap.entrySet().forEach(System.out::println);
	}
	
	public Integer getTotalValueOfAvailableCoins() {

		Integer maxcoinsAvailable = paymentConfig.getMaxCoins();

		List <Integer> coinsList = paymentConfig.getAvailableCoins();
		//System.out.println("Available Coins" + Arrays.asList(coinsList));

		return coinsList
        .stream()
        .collect(Collectors.summingInt(e -> e * maxcoinsAvailable));
		
	}
	

}
