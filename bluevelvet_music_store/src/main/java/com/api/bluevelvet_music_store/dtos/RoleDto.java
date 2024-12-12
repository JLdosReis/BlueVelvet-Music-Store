package com.api.bluevelvet_music_store.dtos;

import com.api.bluevelvet_music_store.enums.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleDto(
        @NotNull
        RoleName roleName,
        @NotBlank
        String description
) {
}