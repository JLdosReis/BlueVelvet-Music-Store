package com.api.bluevelvet_music_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BluevelvetMusicStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BluevelvetMusicStoreApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
