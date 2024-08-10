package com.hospital.management.service.auth;

import com.hospital.management.entity.BaseUser;
import com.hospital.management.entity.Role;
import com.hospital.management.entity.User;
import com.hospital.management.handleexception.UserAlreadyExistException;
import com.hospital.management.payload.UserDto;
import com.hospital.management.repository.BaseUserRepository;
import com.hospital.management.repository.RoleRepository;
import com.hospital.management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BaseUserRepository baseUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerNewUser(UserDto userDto) throws UserAlreadyExistException {
        BaseUser user = this.baseUserRepository.findByEmail(userDto.getEmail());
        if (user != null) {
            throw new UserAlreadyExistException("An account for that email already exists.");
        } else {
            User newUser = new User();
            newUser.setId(userDto.getId());
            newUser.setFirstname(userDto.getFirstname().trim());
            newUser.setLastname(userDto.getLastname().trim());
            newUser.setPhone(userDto.getPhone().trim());
            newUser.setEmail(userDto.getEmail().trim());
            newUser.setPassword(passwordEncoder.encode(userDto.getPassword()).trim());
            newUser.setCreatedAt(LocalDateTime.now());
            Role role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                role = createUserRole();
            }
            newUser.setRoles(Set.of(role));
            this.userRepository.save(newUser);
            return modelMapper.map(newUser, UserDto.class);
        }
    }

    private Role createUserRole() {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
        return role;
    }

}
