package com.humanbooster.authentification.exception;

import com.humanbooster.authentification.models.ErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidation>> handleException(MethodArgumentNotValidException ex){
        List<ErrorValidation> errors = new ArrayList<ErrorValidation>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError error: fieldErrors){
            errors.add(new ErrorValidation(error.getField(), error.getDefaultMessage()));
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
