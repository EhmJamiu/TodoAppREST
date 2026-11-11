package com.ehmjamiu.learn.dto;

public record ResetTokenDto(
        String resetToken,
        String newPassword
) {
}
