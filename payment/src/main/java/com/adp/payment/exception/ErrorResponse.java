package com.adp.payment.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private HttpStatus httpStatus;

    private LocalDateTime timestamp;

    private String message;

    private String details;
    
    private List<ValidationError> errors;

    public ErrorResponse(String message2, HttpStatus badRequest) {
		// TODO Auto-generated constructor stub
    	this.httpStatus = badRequest;
    	this.message = message2;
	}
	@Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {

		private final  String field;
        private final String message;
        
        public ValidationError(String field2, String message2) {
			// TODO Auto-generated constructor stub
        	this.field = field2;
        	this.message = message2;
		}
    }

    public void addValidationError(String field, String message){
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, message));
    }
}
