package com.hospital.management.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.handleexception.DataNotFoundException;
import com.hospital.management.payload.DoctorDto;
import com.hospital.management.service.doctor.DoctorService;
import com.hospital.management.utils.ApiResponse;

@RestController
@RequestMapping("/user")
public class UserDoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctors/all")
    public ResponseEntity<ApiResponse<List<DoctorDto>>> getDoctors() {
        ApiResponse<List<DoctorDto>> response = new ApiResponse<>();
        try {
            List<DoctorDto> doctorDtos = doctorService.getAllDoctors();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(doctorDtos);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctors/doctorId={doctorId}")
    public ResponseEntity<ApiResponse<DoctorDto>> getDoctorById(@PathVariable("doctorId") int id) {
        ApiResponse<DoctorDto> response = new ApiResponse<>();
        try {
            DoctorDto doctorDto = doctorService.getDoctorById(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(doctorDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctors/specializationId={specializationId}")
    public ResponseEntity<ApiResponse<List<DoctorDto>>> getDoctorBySpecializationId(
            @PathVariable("specializationId") int id) {
        ApiResponse<List<DoctorDto>> response = new ApiResponse<>();
        try {
            List<DoctorDto> doctorDto = doctorService.getBySpecializtion(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(doctorDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctors/get/keyword={keyword}")
    public ResponseEntity<ApiResponse<List<DoctorDto>>> filterDoctor(
            @PathVariable("keyword") String keyword) {
        ApiResponse<List<DoctorDto>> response = new ApiResponse<>();
        try {
            List<DoctorDto> doctorDto = doctorService.filtrDoctorByName(keyword);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(doctorDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
