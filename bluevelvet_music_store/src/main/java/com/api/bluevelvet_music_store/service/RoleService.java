package com.api.bluevelvet_music_store.service;

import com.api.bluevelvet_music_store.enums.RoleName;
import com.api.bluevelvet_music_store.models.RoleModel;
import com.api.bluevelvet_music_store.repositories.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean existsByRoleName(RoleName roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

    public RoleModel save(RoleModel role) {
        return roleRepository.save(role);
    }

    public Page<RoleModel> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Optional<RoleModel> findById(Long id) {
        return roleRepository.findById(id);
    }

    public void delete(RoleModel role) {
        roleRepository.delete(role);
    }
}
