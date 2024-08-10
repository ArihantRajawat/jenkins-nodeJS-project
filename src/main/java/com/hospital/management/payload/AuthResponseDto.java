package com.hospital.management.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@RequiredArgsConstructor
public class AuthResponseDto {
    private int status;
    private String message;
    private String token;
    private UserDetails userDetails;
}
