package com.hospital.management.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.handleexception.DataNotFoundException;
import com.hospital.management.handleexception.DuplicateException;
import com.hospital.management.payload.SpecialistDto;
import com.hospital.management.service.specialist.SpecialistService;
import com.hospital.management.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/specialist")
@RequiredArgsConstructor
public class SpecialistController {
    @Autowired
    private SpecialistService specialistService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<SpecialistDto>> addNewSpecialist(
            @Valid @RequestBody SpecialistDto specialistDto) {
        ApiResponse<SpecialistDto> response = new ApiResponse<>();
        try {
            SpecialistDto specialist = specialistService.addSpecialist(specialistDto);
            if (specialist != null) {
                response.setStatus(HttpStatus.CREATED.value());
                response.setMessage("New Doctor Specialist Added");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Some error occurred, Try again");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (DuplicateException e) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something Wrong on Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

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

    @GetMapping("/get/id={specialistId}")
    public ResponseEntity<ApiResponse<SpecialistDto>> getSpecialist(
            @PathVariable("specialistId") int specialistId) {
        ApiResponse<SpecialistDto> response = new ApiResponse<>();
        try {
            SpecialistDto specialist = specialistService.getSpecialistById(specialistId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(specialist);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something Wrong on Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update/id={specialistId}")
    public ResponseEntity<ApiResponse<SpecialistDto>> updateSpecialist(
            @PathVariable("specialistId") int specialistId, @Valid @RequestBody SpecialistDto specialistDto) {
        ApiResponse<SpecialistDto> response = new ApiResponse<>();
        try {
            SpecialistDto specialist = specialistService.updateSpecialist(specialistId, specialistDto);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Specialist updated");
            response.setData(specialist);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DuplicateException e) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something Wrong on Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/id={specialistId}")
    public ResponseEntity<ApiResponse<SpecialistDto>> deleteSpecialist(
            @PathVariable("specialistId") int specialistId) {
        ApiResponse<SpecialistDto> response = new ApiResponse<>();
        try {
            if (specialistService.deleteSpecialist(specialistId)) {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Specialist deleted");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Some error occurred, Try again");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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

    @GetMapping("/get/paginated")
    public ResponseEntity<ApiResponse<Page<SpecialistDto>>> getPaginatedSpecialist(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ApiResponse<Page<SpecialistDto>> response = new ApiResponse<>();
        try {
            Page<SpecialistDto> specialists = specialistService.getPaginatedSpecialist(page, size);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(specialists);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something Wrong on Server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
