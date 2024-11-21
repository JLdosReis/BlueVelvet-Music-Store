package com.api.bluevelvet_music_store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoDto(
        @NotBlank String productName,
        @NotBlank String brand,
        @NotBlank String category,
        @NotNull BigDecimal price,
        @NotNull BigDecimal discount
) {
}