package com.hospital.management.controller;

import com.hospital.management.handleexception.UserAlreadyExistException;
import com.hospital.management.payload.DoctorDto;
import com.hospital.management.payload.UserDto;
import com.hospital.management.service.auth.AuthService;
import com.hospital.management.service.doctor.DoctorService;
import com.hospital.management.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    @Autowired
    private AuthService authService;
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register/save")
    public ResponseEntity<ApiResponse<UserDto>> registerNewPatient(@Valid @RequestBody UserDto userDto) {
        ApiResponse<UserDto> response = new ApiResponse<>();
        try {
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Password do not match");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            UserDto userResponseDto = this.authService.registerNewUser(userDto);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("New user registered.");
            response.setData(userResponseDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistException exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/admin/doctor/add/save")
    public ResponseEntity<ApiResponse<DoctorDto>> registerNewDoctor(@Valid @ModelAttribute DoctorDto doctorDto) {
        ApiResponse<DoctorDto> response = new ApiResponse<>();
        try {
            if (!doctorDto.getPassword().equals(doctorDto.getConfirmPassword())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Password do not match.");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            DoctorDto doctor = doctorService.registerNewDoctor(doctorDto, doctorDto.getImage());
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("New doctor registered.");
            response.setData(doctor);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistException e) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
