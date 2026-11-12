package com.ehmjamiu.learn.dto;

import java.util.UUID;

public record ResetTokenResponse(
        UUID resetToken,
        long expiresAt,
        String email
) {

}