package com.adp.payment.util;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.adp.payment.config.PaymentConfiguration;

public class BillParamValidator implements ConstraintValidator<BillParamConstraint, Integer> {

	@Autowired
	PaymentConfiguration paymentConfig;

	 @Override
	 public void initialize(BillParamConstraint bill) {
	 }
	 
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
//		return (value != null) && (paymentConfig.getAcceptedBills().contains(value));
		return (value != null);
	}


	


}
