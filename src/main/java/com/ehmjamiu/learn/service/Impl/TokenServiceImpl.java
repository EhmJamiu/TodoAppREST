package com.ehmjamiu.learn.service.Impl;

import com.ehmjamiu.learn.dto.RefreshTokenResponse;
import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.entity.Token;
import com.ehmjamiu.learn.exceptionHandler.CustomBadRequest;
import com.ehmjamiu.learn.exceptionHandler.CustomNotFound;
import com.ehmjamiu.learn.repo.TodoUserRepository;
import com.ehmjamiu.learn.repo.TokenRepository;
import com.ehmjamiu.learn.service.JwtService;
import com.ehmjamiu.learn.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {


    private final JwtService jwtService;
    private final TodoUserRepository todoUserRepository;
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(JwtService jwtService, TodoUserRepository todoUserRepository, TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.todoUserRepository = todoUserRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public RefreshTokenResponse getRefreshToken(String accessToken) {
        String email = jwtService.extractUsername(accessToken);
        Token token = tokenRepository.findTokenByUserUsername(email).orElseThrow(()
                -> new CustomNotFound("Refresh token not found"));
        if(!isRefreshTokenValid(token.getRefreshToken())) {
            throw new CustomBadRequest("Refresh token is invalid, please try log in again");
        }
        LocalDateTime refreshTokenExpiresAt = jwtService.expirationDate(token.getRefreshToken());
        LocalDateTime accessTokenExpiresAt = jwtService.expirationDate(accessToken);
        return new RefreshTokenResponse(token.getRefreshToken(), accessToken,
                calculateExpiresIn(refreshTokenExpiresAt),
                calculateExpiresIn(accessTokenExpiresAt),
                token.isRevoked());

    }

    @Override
    public RefreshTokenResponse generateRefreshToken(String username) {
        if(username == null) {
            throw new CustomBadRequest("Username cannot be null");
        }
        String accessToken = jwtService.generateAccessToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);
        LocalDateTime accessTokenExpiresAt = jwtService.expirationDate(accessToken);
        LocalDateTime refreshTokenExpiresAt = jwtService.expirationDate(refreshToken);
        TodoUser user = todoUserRepository.findByUsername(username).orElseThrow(()
                -> new CustomNotFound("User with email " + username + " not found"));
        if(tokenRepository.existsByUserUsername(username)){
            Token existingToken = tokenRepository.findTokenByUserUsername(username).orElseThrow(()
                    -> new CustomNotFound("Token not found"));
            existingToken.setRefreshToken(refreshToken);
            existingToken.setExpiresAt(refreshTokenExpiresAt);
            existingToken.setRevoked(false);
            tokenRepository.save(existingToken);
        }else {
            saveToken(refreshToken, user);
        }
        return new RefreshTokenResponse(refreshToken,
                accessToken,
                calculateExpiresIn(refreshTokenExpiresAt),
                calculateExpiresIn(accessTokenExpiresAt),
                false);

    }

    @Override
    public void saveToken(String refreshToken, TodoUser user) {
        Token token = new Token();
        token.setUser(user);
        token.setRefreshToken(refreshToken);
        token.setIssueAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMonths(6));
        token.setRevoked(false);
        tokenRepository.save(token);

    }

    @Override
    public boolean isRefreshTokenValid(String refreshToken) {
        Optional<Token> token = tokenRepository.findTokenByRefreshToken(refreshToken);
        return token.isPresent() && !token.get().isRevoked() &&token.get().getExpiresAt().isAfter(LocalDateTime.now());

    }

    @Override
    public void revokeRefreshToken(String refreshToken) {
        Optional<Token> token = tokenRepository.findTokenByRefreshToken(refreshToken);
        if (token.isPresent()) {
            token.get().setRevoked(true);
            tokenRepository.save(token.get());
        }
    }

    @Override
    public void revokeAndDeleteTokenForUser(Integer userId) {
        tokenRepository.deleteTokenByUserId(userId);

    }

    public static long calculateExpiresIn(LocalDateTime expiresAt) {
        return Math.max(0, expiresAt.toEpochSecond(ZoneOffset.UTC) - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    }
}