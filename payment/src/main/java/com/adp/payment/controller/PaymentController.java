package com.adp.payment.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.adp.payment.config.PaymentConfiguration;
import com.adp.payment.service.PaymentService;
import com.adp.payment.util.BillParamConstraint;



@RestController
@Validated
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	
	@Autowired
	PaymentService service;

	@Autowired
	PaymentConfiguration paymentConfig;

	
	@GetMapping(path="/change")
	public  ResponseEntity<Map<Integer,Integer>> changeBill(@Valid @BillParamConstraint @RequestParam(required=true) @ModelAttribute("bill") @Min(1) Integer bill) {

		
			Map<Integer,Integer> changeDue = service.getChangeDue(bill);
			return  new ResponseEntity<>(changeDue, HttpStatus.OK);

	  }

    @RequestMapping(value="/error", produces="application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handle(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("reason", request.getAttribute("javax.servlet.error.message"));

        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
