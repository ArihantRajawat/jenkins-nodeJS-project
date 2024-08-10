package com.hospital.management.service.specialist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.management.entity.Specialist;
import com.hospital.management.handleexception.DataNotFoundException;
import com.hospital.management.handleexception.DuplicateException;
import com.hospital.management.payload.SpecialistDto;
import com.hospital.management.repository.SpecialistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    @Autowired
    private SpecialistRepository specialistRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SpecialistDto addSpecialist(SpecialistDto specialistDto) {
        Specialist specialist = new Specialist();
        if (specialistRepository.existsByName(specialistDto.getName())) {
            throw new DuplicateException("Specialization already exists");
        } else {
            specialist.setName(specialistDto.getName());
            specialist.setDescription(specialistDto.getDescription());
            specialistRepository.save(specialist);
            return modelMapper.map(specialist, SpecialistDto.class);
        }
    }

    @Override
    public List<SpecialistDto> getAllSpecialist() {
        List<Specialist> specialists = specialistRepository.findAll();
        return specialists.stream().map(specialist -> modelMapper.map(specialist, SpecialistDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public SpecialistDto getSpecialistById(int id) {
        Optional<Specialist> specialist = specialistRepository.findById(id);
        if (specialist.isPresent()) {
            return modelMapper.map(specialist.get(), SpecialistDto.class);
        } else {
            throw new DataNotFoundException("No any data found");
        }
    }

    @Override
    public SpecialistDto updateSpecialist(int id, SpecialistDto specialistDto) {
        Specialist eSpecialist = specialistRepository.findByName(specialistDto.getName());
        Specialist existexistingSpecialist = specialistRepository.findByIdAndNameIgnoreCase(specialistDto.getId(),
                specialistDto.getName());
        if (eSpecialist != null) {
            if (!existexistingSpecialist.getId().equals(specialistDto.getId())) {
                throw new DuplicateException("Specialization already exists");
            }
        }

        Optional<Specialist> optionalSpecialist = specialistRepository.findById(id);
        if (optionalSpecialist.isPresent()) {
            Specialist specialist = optionalSpecialist.get();
            specialist.setName(specialistDto.getName());
            specialist.setDescription(specialistDto.getDescription());
            specialistRepository.save(specialist);
            return modelMapper.map(specialist, SpecialistDto.class);
        } else {
            throw new DataNotFoundException("No any data found");
        }
    }

    @Override
    public boolean deleteSpecialist(int id) {
        boolean status = false;
        Optional<Specialist> specialist = specialistRepository.findById(id);
        if (specialist.isPresent()) {
            specialistRepository.deleteById(id);
            return true;
        }
        return status;
    }

    @Override
    public List<SpecialistDto> filtrSpecialist(String keyword) {
        List<Specialist> specialists = specialistRepository.findByNameContainingIgnoreCase(keyword);
        return specialists.stream().map(specialist -> modelMapper.map(specialist, SpecialistDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<SpecialistDto> getPaginatedSpecialist(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Specialist> specialists = specialistRepository.findAll(pageable);
        return specialists.map(specialist -> convertToSpecialistDto(specialist));
    }

    private SpecialistDto convertToSpecialistDto(Specialist specialist) {
        return modelMapper.map(specialist, SpecialistDto.class);
    }

}
