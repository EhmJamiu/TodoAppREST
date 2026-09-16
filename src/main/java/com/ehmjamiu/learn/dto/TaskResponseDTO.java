package com.ehmjamiu.learn.dto;

import com.ehmjamiu.learn.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponseDTO(
        String title,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        TaskStatus status
) {
}
