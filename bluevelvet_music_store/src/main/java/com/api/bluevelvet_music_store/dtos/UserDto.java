package com.api.bluevelvet_music_store.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @NotBlank
        String password,
        @NotBlank
        String userPhoto,
        @NotNull
        boolean enable,
        @NotNull
        List<RoleDto> roles
) {
}
