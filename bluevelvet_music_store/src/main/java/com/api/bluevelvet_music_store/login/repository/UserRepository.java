package com.api.bluevelvet_music_store.login.repository;

import com.api.bluevelvet_music_store.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Encontrar um usuário por email (já está no código original)
    Optional<User> findByEmail(String email);

    // Método opcional: encontrar todos os usuários com um papel específico (por exemplo, "admin")
    List<User> findByRole(String role);
}