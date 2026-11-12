package com.ehmjamiu.learn.dto;

import com.ehmjamiu.learn.entity.enums.TaskStatus;

public record TaskStatusRequest(
        TaskStatus status
) {
}
