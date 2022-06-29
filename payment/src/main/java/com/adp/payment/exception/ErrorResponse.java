package com.adp.payment.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String message;

    private List<ValidationError> errors;

    public ErrorResponse(String message2, int status2) {
    	this.status = status2;
    	this.message = message2;
    }
    public ErrorResponse(HttpStatus badRequest, String message2) {
		// TODO Auto-generated constructor stub
    	this.status = badRequest.value();
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
