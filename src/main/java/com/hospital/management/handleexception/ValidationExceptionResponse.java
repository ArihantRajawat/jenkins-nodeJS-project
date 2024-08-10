package com.hospital.management.handleexception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class ValidationExceptionResponse {
    private int status;
    private String message;
    private Map<String, String> errors;

    public ValidationExceptionResponse(int status, String message, Map<String, String> errors){
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
