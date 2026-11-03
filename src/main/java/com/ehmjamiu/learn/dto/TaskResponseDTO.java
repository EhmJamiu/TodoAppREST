package com.ehmjamiu.learn.dto;

import com.ehmjamiu.learn.entity.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponseDTO(
        String title,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        TaskStatus status
) {
}
