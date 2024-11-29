package com.api.bluevelvet_music_store.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoDto(
        @NotBlank String productName,
        @NotBlank String brand,
        @NotBlank String category,
        @NotNull BigDecimal price,
        @NotNull BigDecimal discount,

        @NotNull boolean enabled,
        @NotNull boolean inStock,

        @NotBlank String shortDescription,
        @NotBlank String fullDescription,

        @Valid DimensoesDto dimensions,
        @Valid List<ImagemDto> images
) {
}