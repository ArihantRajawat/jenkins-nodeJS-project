package com.hospital.management.service.token;

import com.hospital.management.entity.InvalidToken;
import com.hospital.management.repository.InvalidTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenManagementImpl implements TokenManagement{
    @Autowired
    private InvalidTokenRepository tokenRepository;


    @Override
    public boolean isTokenInvalid(String token) {
        return this.tokenRepository.existsByToken(token);
    }

    @Override
    public void invalidateToken(String token, long userId) {
        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setToken(token);
        invalidToken.setExpirationTime(LocalDateTime.now());
        invalidToken.setUserId(userId);
        this.tokenRepository.save(invalidToken);
    }
}
