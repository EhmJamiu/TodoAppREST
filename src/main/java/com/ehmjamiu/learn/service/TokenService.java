package com.ehmjamiu.learn.service;

import com.ehmjamiu.learn.dto.RefreshTokenResponse;
import com.ehmjamiu.learn.entity.TodoUser;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    RefreshTokenResponse getRefreshToken(String token);

    RefreshTokenResponse generateRefreshToken(String username);

    void saveToken(String refreshToken, TodoUser user);
    boolean isRefreshTokenValid(String refreshToken);
    void revokeRefreshToken(String refreshToken);
    void revokeAndDeleteTokenForUser(Integer userId);

}