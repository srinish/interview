package com.adp.payment.exception;


import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
    // Let Spring BasicErrorController handle the exception, we just override the status code
    @ExceptionHandler(InvalidBillAmountException.class)
    public ResponseEntity handleInvalidBillAmountException( InvalidBillAmountException invalidBillAmountException)  {
    	ErrorResponse errorResponse = new ErrorResponse(invalidBillAmountException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity("Invalid Bill Amount", HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NotEnoughCoinsException.class)
    public ResponseEntity handleNotEnoughCoinsException(NotEnoughCoinsException notEnoughCoinsException) throws IOException {
        return new ResponseEntity("Required coins does not exist", HttpStatus.METHOD_NOT_ALLOWED);
    }

    // @Validate For Validating Path Variables and Request Parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }
}
