package com.api.bluevelvet_music_store.login.controle;

import com.api.bluevelvet_music_store.login.model.User;
import com.api.bluevelvet_music_store.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // Ajuste conforme o domínio do front-end
public class AuthController {

    @Autowired
    private UserService userService;

    // Endpoint para registrar um novo usuário
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Chama o serviço para registrar o usuário
        User createdUser = userService.registerUser(user.getEmail(), user.getPassword(), user.getRole());
        return ResponseEntity.ok(createdUser);
    }

    // Endpoint para fazer login de um usuário
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        try {
            // Autentica o usuário
            User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());

            // Prepara a resposta com a mensagem e o role
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login bem-sucedido!");
            response.put("role", authenticatedUser.getRole()); // Retorna o role (user ou admin)

            // Retorna a resposta com o role
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Em caso de erro (credenciais inválidas), retorna uma mensagem de erro
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Credenciais inválidas.");
            return ResponseEntity.status(400).body(errorResponse);
        }
    }
}