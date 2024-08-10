package com.hospital.management.service.query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.management.entity.PatientQuery;
import com.hospital.management.payload.PatientQueryDto;
import com.hospital.management.repository.PatientQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientQueryServiceImpl implements PatientQueryService {

    @Autowired
    private PatientQueryRepository patientQueryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientQueryDto addQuery(PatientQueryDto patientQueryDto) {
        PatientQuery patientQuery = new PatientQuery();
        patientQuery.setName(patientQueryDto.getName().trim());
        patientQuery.setPhone(patientQueryDto.getPhone().trim());
        patientQuery.setEmail(patientQueryDto.getEmail().trim());
        patientQuery.setQuery(patientQueryDto.getQuery().trim());
        patientQueryRepository.save(patientQuery);
        return modelMapper.map(patientQuery, PatientQueryDto.class);
    }

}
