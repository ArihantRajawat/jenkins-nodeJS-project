package com.hospital.management.config;

import java.time.LocalDateTime;
import java.util.Set;

import com.hospital.management.entity.Admin;
import com.hospital.management.entity.Role;
import com.hospital.management.repository.AdminRepository;
import com.hospital.management.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountInitializer implements ApplicationRunner {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public AdminAccountInitializer(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (adminAccountExists()) {
            createAdminAccount();
        }
    }

    private boolean adminAccountExists() {
        Admin admin = this.adminRepository.findByEmail("dinesh7627000@gmail.com");
        if (admin == null) {
            return true;
        } else {
            return false;
        }
    }

    private void createAdminAccount() {
        Admin admin = new Admin();
        admin.setFirstname("Dinesh");
        admin.setLastname("Kumawat");
        admin.setPhone("917627000907");
        admin.setEmail("dinesh7627000@gmail.com");
        admin.setPassword(passwordEncoder.encode("Admin"));
        admin.setCreatedAt(LocalDateTime.now());
        Role role = roleRepository.findByName("ADMIN");
        if (role == null) {
            role = createRole();
        }
        admin.setRoles(Set.of(role));
        this.adminRepository.save(admin);
    }

    private Role createRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        roleRepository.save(role);
        return role;
    }
}
