package com.api.bluevelvet_music_store.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuário não encontrado: " + id);
    }
}