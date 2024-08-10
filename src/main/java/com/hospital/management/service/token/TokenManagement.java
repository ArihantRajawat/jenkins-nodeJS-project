package com.hospital.management.service.token;

public interface TokenManagement {
    boolean isTokenInvalid(String token);
    void invalidateToken(String token, long userId);
}
