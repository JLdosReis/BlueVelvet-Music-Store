package com.api.bluevelvet_music_store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImagemDto(
        @NotBlank String imgData,
        @NotNull boolean isImgMain
) {
}
