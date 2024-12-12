package com.api.bluevelvet_music_store.exceptions;

public class EmailConflictException extends RuntimeException {
    public EmailConflictException(String email) {
        super("Conflito: o email " + email + " já está em uso.");
    }
}
