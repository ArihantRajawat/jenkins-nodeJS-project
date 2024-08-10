package com.hospital.management.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.handleexception.DataNotFoundException;
import com.hospital.management.payload.DoctorDto;
import com.hospital.management.payload.DoctorUpdateDto;
import com.hospital.management.service.doctor.DoctorService;
import com.hospital.management.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<DoctorDto>>> getAllDoctors() {
        ApiResponse<List<DoctorDto>> response = new ApiResponse<>();
        try {
            List<DoctorDto> doctorDtos = doctorService.getAllDoctors();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(doctorDtos);
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

    @GetMapping("/get/paginated")
    public ResponseEntity<ApiResponse<Page<DoctorDto>>> getPaginatedDoctors(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size) {
        ApiResponse<Page<DoctorDto>> response = new ApiResponse<>();
        try {
            Page<DoctorDto> pageableData = doctorService.getPaginatedDoctors(page, size);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Data found");
            response.setData(pageableData);
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

    @GetMapping("/get/id={doctorId}")
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

    @PutMapping("/update/id={doctorId}")
    public ResponseEntity<ApiResponse<DoctorUpdateDto>> updateDoctor(@PathVariable("doctorId") int id,
            @Valid @ModelAttribute DoctorUpdateDto doctorUpdateDto) {
        ApiResponse<DoctorUpdateDto> response = new ApiResponse<>();
        try {
            DoctorUpdateDto doctor = doctorService.updateDoctor(id, doctorUpdateDto, doctorUpdateDto.getImage());
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Doctor Detail Updated");
            response.setData(doctor);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/id={doctorId}")
    public ResponseEntity<ApiResponse<DoctorDto>> deleteDoctor(@PathVariable("doctorId") int id) {
        ApiResponse<DoctorDto> response = new ApiResponse<>();
        try {
            doctorService.deleteDoctor(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Doctor deleted seccess");
            response.setData(null);
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
