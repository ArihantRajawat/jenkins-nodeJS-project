package com.hospital.management.service.auth;

import com.hospital.management.handleexception.UserAlreadyExistException;
import com.hospital.management.payload.UserDto;

public interface AuthService {
    UserDto registerNewUser(UserDto userDto) throws UserAlreadyExistException;

}
