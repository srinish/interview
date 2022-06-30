package com.adp.payment.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import lombok.Data;



@ConfigurationProperties(prefix="custom")
@Validated
public class PaymentConfiguration {
	@NotNull
	Integer maxCoins;
	
	@NotNull
	List<Integer> availableCoins;
	
	@NotNull
	List<Integer> acceptedBills;

	Map<Integer, Integer> coinBalanceMap;
	
	public Integer getMaxCoins() {
		return maxCoins;
	}

	public void setMaxCoins(Integer maxCoins) {
		this.maxCoins = maxCoins;
	}

	public List<Integer> getAvailableCoins() {
		return availableCoins;
	}

	public void setAvailableCoins(List<Integer> availableCoins) {
		this.availableCoins = availableCoins;
	}

	public List<Integer> getAcceptedBills() {
		return acceptedBills;
	}

	public void setAcceptedBills(List<Integer> acceptedBills) {
		this.acceptedBills = acceptedBills;
	}

	@Bean
	public Map<Integer,Integer> coinBalanceMap(){

			List<Integer> coinsList = getAvailableCoins();
			coinBalanceMap = coinsList.stream().collect(Collectors.toMap(i -> i, i -> getMaxCoins()));
			return coinBalanceMap;
	}
	
	
}
