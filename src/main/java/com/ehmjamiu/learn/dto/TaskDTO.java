package com.ehmjamiu.learn.dto;

import jakarta.validation.constraints.NotNull;

public record TaskDTO(
        @NotNull
        String title
) {

}
