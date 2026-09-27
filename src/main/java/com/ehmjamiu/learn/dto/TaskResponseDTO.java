package com.ehmjamiu.learn.dto;

import com.ehmjamiu.learn.entity.TaskStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record TaskResponseDTO(
        long id,
        String title,
        String description,
        LocalTime createdAt,
        LocalTime updatedAt,
        TaskStatus status
) {
}
