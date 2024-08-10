package com.hospital.management.service.doctor;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.hospital.management.handleexception.UserAlreadyExistException;
import com.hospital.management.payload.DoctorDto;
import com.hospital.management.payload.DoctorUpdateDto;

public interface DoctorService {
    DoctorDto registerNewDoctor(DoctorDto doctorDto, MultipartFile file) throws UserAlreadyExistException, IOException;

    List<DoctorDto> getAllDoctors();

    DoctorDto getDoctorById(int id);

    DoctorUpdateDto updateDoctor(int id, DoctorUpdateDto doctorUpdateDto, MultipartFile file)
            throws UserAlreadyExistException;

    boolean deleteDoctor(int id);

    Page<DoctorDto> getPaginatedDoctors(int page, int size);

    List<DoctorDto> getBySpecializtion(int id);

    List<DoctorDto> filtrDoctorByName(String keyword);

    long getTotalDoctor();
}
