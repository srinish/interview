package com.adp.payment.exception;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
    // Let Spring BasicErrorController handle the exception, we just override the status code
    @ExceptionHandler(InvalidBillAmountException.class)
    public ResponseEntity handleInvalidBillAmountException( InvalidBillAmountException invalidBillAmountException)  {
//    	ErrorResponse errorResponse = new ErrorResponse(invalidBillAmountException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(invalidBillAmountException.getMessage(), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NotEnoughCoinsException.class)
    public ResponseEntity handleNotEnoughCoinsException(NotEnoughCoinsException notEnoughCoinsException) throws IOException {
    	ErrorResponse errorResponse = new ErrorResponse(notEnoughCoinsException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(notEnoughCoinsException.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + 
              violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ErrorResponse errorResp = 
          new ErrorResponse( Arrays.asList(errors).toString(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(
          errorResp, new HttpHeaders(),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = 
          ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ErrorResponse errorResp = 
          new ErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(
          errorResp, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
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
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers, 
      HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        
        ErrorResponse errorResp = 
          new ErrorResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(
        		error, new HttpHeaders(), status);
    }
}
