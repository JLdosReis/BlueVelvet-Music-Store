package com.api.bluevelvet_music_store.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credenciais inválidas.");
    }
}
