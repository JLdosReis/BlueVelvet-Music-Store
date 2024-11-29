package com.api.bluevelvet_music_store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DimensoesDto(
        @NotNull BigDecimal width,
        @NotNull BigDecimal height,
        @NotNull BigDecimal length,
        @NotNull BigDecimal weight,
        @NotBlank String unit,
        @NotBlank String unitWeight
) {
}
