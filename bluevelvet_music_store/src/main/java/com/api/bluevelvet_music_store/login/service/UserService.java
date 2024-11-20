package com.api.bluevelvet_music_store.login.service;

import com.api.bluevelvet_music_store.login.model.User;
import com.api.bluevelvet_music_store.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Método para registrar um usuário
    public User registerUser(String email, String password, String role) {
        // Verificar se o usuário já existe
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe.");
        }

        // Criar novo usuário com o role
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Senha criptografada
        user.setRole(role); // Atribui o role ao usuário (pode ser "admin" ou "user")

        return userRepository.save(user);
    }

    // Método para autenticar um usuário
    public User authenticateUser(String email, String password) {
        // Tenta encontrar o usuário no banco de dados
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword())) // Verifica a senha criptografada
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas."));
    }
}