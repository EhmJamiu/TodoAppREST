package com.ehmjamiu.learn.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "Username cannot be null")
        String username,
        @NotNull(message = "Enter appropriate password")
        String password
) {
}
