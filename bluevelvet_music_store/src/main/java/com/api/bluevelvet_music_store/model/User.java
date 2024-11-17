package com.api.bluevelvet_music_store.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String role;
}
