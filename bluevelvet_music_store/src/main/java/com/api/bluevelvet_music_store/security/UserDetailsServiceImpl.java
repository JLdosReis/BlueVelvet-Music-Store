package com.api.bluevelvet_music_store.security;

import com.api.bluevelvet_music_store.dtos.UserDto;
import com.api.bluevelvet_music_store.mappers.UserMapper;
import com.api.bluevelvet_music_store.models.UserModel;
import com.api.bluevelvet_music_store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;
    final UserMapper userMapper;

    public UserDetailsServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserModel save(UserModel userModel){
        return userRepository.save(userModel);
    }

    public Page<UserModel> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Optional<UserModel> findById(long id){
        return userRepository.findById(id);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public UserModel toModel(UserDto userDto){
        return userMapper.toModel(userDto);
    }

    @Transactional
    public void delete(UserModel userModel){
        userRepository.delete(userModel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(username)
                .orElseThrow(() ->  new UsernameNotFoundException("Usuario nao encontrado com o email: " + username));
        return new User(userModel.getEmail(),userModel.getPassword(), userModel.isEnable(), true,
                true, true, userModel.getAuthorities());
    }
}
