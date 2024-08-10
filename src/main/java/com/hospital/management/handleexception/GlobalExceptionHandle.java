package com.hospital.management.handleexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ValidationExceptionResponse> handleValidationException(
            MethodArgumentNotValidException exception) {
        ValidationExceptionResponse response = new ValidationExceptionResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("All fields are mandatory");
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : fieldErrors) {
            String fieldName = error.getField();
            String errorDefaultMessage = error.getDefaultMessage();
            errors.put(fieldName, errorDefaultMessage);
        }
        response.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleNoResourceFoundException(NoResourceFoundException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

}
