package com.api.bluevelvet_music_store.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Produto não encontrado: " + id);
    }
}
