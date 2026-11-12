package com.ehmjamiu.learn.dto;

public record AuthResponse(
        String accessToken,
        UserInfo user
) {
}
