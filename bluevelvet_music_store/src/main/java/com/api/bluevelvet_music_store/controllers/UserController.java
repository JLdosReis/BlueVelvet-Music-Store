package com.api.bluevelvet_music_store.controllers;

import com.api.bluevelvet_music_store.dtos.UserDto;
import com.api.bluevelvet_music_store.dtos.UserLoginDto;
import com.api.bluevelvet_music_store.exceptions.EmailConflictException;
import com.api.bluevelvet_music_store.exceptions.InvalidCredentialsException;
import com.api.bluevelvet_music_store.exceptions.UserNotFoundException;
import com.api.bluevelvet_music_store.models.UserModel;
import com.api.bluevelvet_music_store.security.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuario")
public class UserController {

    final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserDetailsServiceImpl userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS')")
    @PostMapping("/registro")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        if(userDetailsService.existsByEmail(userDto.email())){
            throw new EmailConflictException(userDto.email());
        }
        var userModel = new UserModel();
        userModel = userDetailsService.toModel(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsService.save(userModel));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody @Valid UserLoginDto userLoginDto){
        try {
            var userAuth = new UsernamePasswordAuthenticationToken(userLoginDto.email(),
                    userLoginDto.password());
            authenticationManager.authenticate(userAuth);
            return ResponseEntity.ok("Sucesso!");
        } catch (Exception e){
            throw new InvalidCredentialsException();
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS', 'ASSISTENTE')")
    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(@PageableDefault(sort = "userId", direction = Sort.Direction.ASC)
                                                             Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsService.findAll(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS', 'ASSISTENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Long id) {
        Optional<UserModel> userOptional = userDetailsService.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") Long id,
                                                @RequestBody @Valid UserDto userDto){
        Optional<UserModel> userModelOpt = userDetailsService.findById(id);
        if(userModelOpt.isEmpty()){
            throw new UserNotFoundException(id);
        }
        var user = userModelOpt.get();
        user = userDetailsService.toModel(userDto);
        user.setUserId(userModelOpt.get().getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsService.save(user));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GERENTE_VENDAS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") Long id){
        Optional<UserModel> userModelOpt = userDetailsService.findById(id);
        if(userModelOpt.isEmpty()){
            throw new UserNotFoundException(id);
        }
        userDetailsService.delete(userModelOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso.");
    }

}
