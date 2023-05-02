package com.bkabatas.ssozlukproject.service.Impl;

import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.model.UserRefreshToken;
import com.bkabatas.ssozlukproject.repository.UserRefreshTokenRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.Instant;
import java.util.UUID;

@Service
@Data
public class UserRefreshTokenServiceImpl {
    @Value("${refresh.token.expires.in}")
    Long expireSeconds;

    @Autowired
    private UserRefreshTokenRepository refreshTokenRepository;

    public String createRefreshToken(User user) {
        UserRefreshToken token = refreshTokenRepository.findByUserId(user.getId());
        if(token == null) {
            token =	new UserRefreshToken();
            token.setUser(user);
        }
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    public boolean isRefreshExpired(UserRefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }

    public UserRefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }
}
