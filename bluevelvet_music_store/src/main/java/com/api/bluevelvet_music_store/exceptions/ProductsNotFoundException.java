package com.api.bluevelvet_music_store.exceptions;

public class ProductsNotFoundException extends RuntimeException {
    public ProductsNotFoundException(String name) {
        super("Nenhum produto encontrado com o nome: " + name);
    }
}
