package com.api.bluevelvet_music_store.exceptions;

public class RoleConflictException extends RuntimeException {
    public RoleConflictException(String roleName) {
        super("Conflito: o nome da role " + roleName + " já está em uso.");
    }
}
