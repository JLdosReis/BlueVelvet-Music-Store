package com.api.bluevelvet_music_store.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Long id) {
        super("Role não encontrada: " + id);
    }
}
