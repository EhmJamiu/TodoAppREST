package com.ehmjamiu.learn.dto;

public record RefreshTokenResponse(
        String refreshToken,
        String accessToken,
        Long refreshTokenExpiresAt,
        Long accessTokenExpiresAt,
        boolean revoked
) {
}
