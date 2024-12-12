package com.api.bluevelvet_music_store.exceptions;

public class ProductConflictException extends RuntimeException {
    public ProductConflictException(String productName) {
        super("Conflito: o nome do produto " + productName + " já está em uso.");
    }
}
