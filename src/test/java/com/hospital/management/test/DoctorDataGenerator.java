package com.hospital.management.test;


import com.github.javafaker.Faker;
import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.Image;
import com.hospital.management.entity.Role;
import com.hospital.management.entity.Specialist;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.RoleRepository;
import com.hospital.management.repository.SpecialistRepository;
import com.hospital.management.service.cloudinary.CloudinaryImageUploadService;
import com.hospital.management.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class DoctorDataGenerator {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private SpecialistRepository specialistRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CloudinaryImageUploadService cloudinaryImageUploadService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ImageService imageService;
    Faker faker = new Faker();

    @Test
    public void createDoctor() throws IOException {
        for(int i = 0; i < 5; i++){
            Doctor doctor = new Doctor();
            doctor.setFirstname(faker.name().firstName());
            doctor.setLastname(faker.name().lastName());
            doctor.setEmail(faker.internet().emailAddress());
            doctor.setPhone(String.valueOf(faker.phoneNumber().cellPhone()));
            doctor.setActive(true);
            doctor.setConsultationFee(faker.random().nextInt(200, 1000));
            doctor.setQualification(faker.educator().course());
            doctor.setExperience(String.valueOf(faker.number().numberBetween(1, 50)));
            doctor.setPassword(bCryptPasswordEncoder.encode("Doctor"));
            doctor.setLicenceNo(faker.lorem().characters(12).toUpperCase());
            doctor.setAbout(faker.lorem().paragraph(20));
            List<Specialist> specialists = specialistRepository.findAll();
            Specialist randomSpecialist = specialists.get(faker.random().nextInt(specialists.size()));
            doctor.setSpecialization(randomSpecialist);
            doctor.setCreatedAt(LocalDateTime.now());
            Map imageResult = cloudinaryImageUploadService.uploadImage(generateFakeImage());
            doctor.setImageUrl((String) imageResult.get("url"));
            Role doctorRole = roleRepository.findByName("ROLE_DOCTOR");
            if (doctorRole == null) {
                createDoctorRole();
            }
            doctor.setRoles(Set.of(doctorRole));
            doctorRepository.save(doctor);
        }
    }

    public MultipartFile generateFakeImage() throws IOException {
        String imageUrl = "https://picsum.photos/400";
        RestTemplate restTemplate = new RestTemplate();
        byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
        MultipartFile multipartFile = new MockMultipartFile("downloadedImage", imageBytes);

        return multipartFile;
    }

    private Role createDoctorRole() {
        Role role = new Role();
        role.setName("ROLE_DOCTOR");
        return roleRepository.save(role);
    }

}
