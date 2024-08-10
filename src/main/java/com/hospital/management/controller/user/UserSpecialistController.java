package com.hospital.management.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.payload.SpecialistDto;
import com.hospital.management.service.specialist.SpecialistService;
import com.hospital.management.utils.ApiResponse;

@RestController
@RequestMapping("/user/specialist")
public class UserSpecialistController {
    @Autowired
    private SpecialistService specialistService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<SpecialistDto>>> getAllSpecialist() {
        ApiResponse<List<SpecialistDto>> response = new ApiResponse<>();
        try {
            List<SpecialistDto> specialistDtosList = specialistService.getAllSpecialist();
            if (!specialistDtosList.isEmpty()) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Data found");
                response.setData(specialistDtosList);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.setStatus(HttpStatus.FOUND.value());
                response.setMessage("There is no any Data found");
                return ResponseEntity.status(HttpStatus.FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something Wrong on Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/get/keyword={keyword}")
    public ResponseEntity<ApiResponse<List<SpecialistDto>>> getAllFilterSpecialist(
            @PathVariable("keyword") String keyword) {
        ApiResponse<List<SpecialistDto>> response = new ApiResponse<>();
        try {
            List<SpecialistDto> specialistDtosList = specialistService.filtrSpecialist(keyword);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(specialistDtosList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something Wrong on Server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
