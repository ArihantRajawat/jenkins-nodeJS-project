package com.hospital.management.service.specialist;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hospital.management.payload.SpecialistDto;

public interface SpecialistService {
    SpecialistDto addSpecialist(SpecialistDto specialistDto);

    List<SpecialistDto> getAllSpecialist();

    SpecialistDto getSpecialistById(int id);

    SpecialistDto updateSpecialist(int id, SpecialistDto specialistDto);

    boolean deleteSpecialist(int id);

    List<SpecialistDto> filtrSpecialist(String keyword);

    Page<SpecialistDto> getPaginatedSpecialist(int page, int size);

}
