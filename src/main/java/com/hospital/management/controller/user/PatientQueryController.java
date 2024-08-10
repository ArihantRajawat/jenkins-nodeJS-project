package com.hospital.management.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.payload.PatientQueryDto;
import com.hospital.management.service.query.PatientQueryService;
import com.hospital.management.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
public class PatientQueryController {

    @Autowired
    private PatientQueryService patientQueryService;

    @PostMapping("/patientQuery")
    public ResponseEntity<ApiResponse<PatientQueryDto>> addPatientQuery(
            @Valid @RequestBody PatientQueryDto patientQueryDto) {
        ApiResponse<PatientQueryDto> response = new ApiResponse<>();
        try {
            PatientQueryDto patientQuery = patientQueryService.addQuery(patientQueryDto);
            if (patientQuery != null) {
                response.setStatus(HttpStatus.CREATED.value());
                response.setMessage("Your query submitted,We will contact you within 24hrs");
                response.setData(patientQuery);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Opps! Somethig wrong on Server");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Somethig wrong on Server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
