package com.hospital.management.service.doctor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hospital.management.entity.BaseUser;
import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.Role;
import com.hospital.management.entity.Specialist;
import com.hospital.management.handleexception.DataNotFoundException;
import com.hospital.management.handleexception.UserAlreadyExistException;
import com.hospital.management.payload.DoctorDto;
import com.hospital.management.payload.DoctorUpdateDto;
import com.hospital.management.repository.BaseUserRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.RoleRepository;
import com.hospital.management.repository.SpecialistRepository;
import com.hospital.management.service.cloudinary.CloudinaryImageUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private BaseUserRepository baseUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SpecialistRepository specialistRepository;
    @Autowired
    private CloudinaryImageUploadService cloudinaryImageUploadService;

    @Override
    public DoctorDto registerNewDoctor(DoctorDto doctorDto, MultipartFile file)
            throws UserAlreadyExistException, IOException {
        BaseUser user = baseUserRepository.findByEmail(doctorDto.getEmail());
        if (user != null) {
            throw new UserAlreadyExistException("An account for that email already exists.");
        } else {
            Doctor doctor = new Doctor();
            doctor.setId(doctorDto.getId());
            doctor.setFirstname(doctorDto.getFirstname());
            doctor.setLastname(doctorDto.getLastname());
            doctor.setEmail(doctorDto.getEmail());
            doctor.setPhone(doctorDto.getPhone());
            doctor.setPassword(passwordEncoder.encode(doctorDto.getPassword()));
            doctor.setExperience(doctorDto.getExperience());
            doctor.setConsultationFee(doctorDto.getConsultationFee());
            doctor.setLicenceNo(doctorDto.getLicenceNo());
            doctor.setActive(true);
            doctor.setAbout(doctorDto.getAbout());
            Specialist specialist = specialistRepository.findById(doctorDto.getSpecializationId()).orElseThrow();
            doctor.setSpecialization(specialist);
            doctor.setQualification(doctorDto.getQualification());
            doctor.setCreatedAt(LocalDateTime.now());
            Role doctorRole = roleRepository.findByName("ROLE_DOCTOR");
            if (doctorRole == null) {
                createDoctorRole();
            }
            doctor.setRoles(Set.of(doctorRole));
            @SuppressWarnings("rawtypes")
            Map imageResult = cloudinaryImageUploadService.uploadImage(file);
            String url = (String) imageResult.get("url");
            doctor.setImageUrl(url);
            doctorRepository.save(doctor);
            return modelMapper.map(doctor, DoctorDto.class);
        }
    }

    private Role createDoctorRole() {
        Role role = new Role();
        role.setName("ROLE_DOCTOR");
        return roleRepository.save(role);
    }

    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    public DoctorDto getDoctorById(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            return modelMapper.map(optionalDoctor.get(), DoctorDto.class);
        }
        throw new DataNotFoundException("Doctor data not found");
    }

    public DoctorUpdateDto updateDoctor(int id, DoctorUpdateDto doctorUpdateDto, MultipartFile file)
            throws UserAlreadyExistException {
        BaseUser baseUser = baseUserRepository.findByEmail(doctorUpdateDto.getEmail());
        if (baseUser != null) {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
            if (optionalDoctor.isPresent()) {
                Doctor doctor = optionalDoctor.get();
                doctor.setFirstname(doctorUpdateDto.getFirstname());
                doctor.setLastname(doctorUpdateDto.getLastname());
                doctor.setLicenceNo(doctorUpdateDto.getLicenceNo());
                doctor.setPhone(doctorUpdateDto.getPhone());
                doctor.setEmail(doctorUpdateDto.getEmail());
                int specializationId = doctorUpdateDto.getSpecializationId();
                Specialist specialist = specialistRepository.findById(specializationId).orElseThrow();
                doctor.setSpecialization(specialist);
                doctor.setQualification(doctorUpdateDto.getQualification());
                doctor.setAbout(doctorUpdateDto.getAbout());
                doctor.setConsultationFee(doctorUpdateDto.getConsultationFee());
                doctor.setUpdatedAt(LocalDateTime.now());
                @SuppressWarnings("rawtypes")
                Map imageResult = cloudinaryImageUploadService.uploadImage(file);
                String url = (String) imageResult.get("url");
                doctor.setImageUrl(url);
                doctorRepository.save(doctor);
                return modelMapper.map(doctor, DoctorUpdateDto.class);
            } else {
                throw new UsernameNotFoundException("Doctor not found");
            }
        } else {
            throw new UserAlreadyExistException("An account for that email already exists.");
        }
    }

    public boolean deleteDoctor(int id) {
        boolean status = false;
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            doctorRepository.deleteById(id);
            status = true;
        } else {
            throw new UsernameNotFoundException("Doctor not found");
        }
        return status;
    }

    @Override
    public Page<DoctorDto> getPaginatedDoctors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
        return doctorPage.map(doctor -> convertToDoctorDto(doctor));
    }

    private DoctorDto convertToDoctorDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDto.class);
    }

    @Override
    public List<DoctorDto> getBySpecializtion(int id) {
        Optional<Specialist> specialist = specialistRepository.findById(id);
        if (specialist.isPresent()) {
            List<Doctor> doctors = doctorRepository.findBySpecialization(specialist.get());
            return doctors.stream().map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<DoctorDto> filtrDoctorByName(String keyword) {
        List<Doctor> filterDoctor = doctorRepository
                .findByFirstnameContainingIgnoreCase(keyword);
        return filterDoctor.stream().map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalDoctor() {
        return doctorRepository.count();
    }
}
