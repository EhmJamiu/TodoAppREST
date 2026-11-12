package com.ehmjamiu.learn.dto;

import com.ehmjamiu.learn.entity.enums.TaskStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record TaskResponseDTO(
        long id,
        String title,
        //String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        TaskStatus status
) {
}
