package com.api.bluevelvet_music_store.controllers;

import com.api.bluevelvet_music_store.dtos.RoleDto;
import com.api.bluevelvet_music_store.exceptions.RoleConflictException;
import com.api.bluevelvet_music_store.exceptions.RoleNotFoundException;
import com.api.bluevelvet_music_store.models.RoleModel;
import com.api.bluevelvet_music_store.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/roles")
public class RoleController {

    final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Object> saveRole(@RequestBody @Valid RoleDto roleDto) {
        if (roleService.existsByRoleName(roleDto.roleName())) {
            throw new RoleConflictException(roleDto.roleName().toString());
        }
        var roleModel = new RoleModel();
        BeanUtils.copyProperties(roleDto, roleModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(roleModel));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    @GetMapping
    public ResponseEntity<Page<RoleModel>> getAllRoles(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                                                       Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneRole(@PathVariable(value = "id") Long id) {
        Optional<RoleModel> roleModelOpt = roleService.findById(id);
        if (roleModelOpt.isEmpty()) {
            throw new RoleNotFoundException(id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(roleModelOpt.get());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRole(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid RoleDto roleDto) {
        Optional<RoleModel> roleModelOpt = roleService.findById(id);
        if (roleModelOpt.isEmpty()) {
            throw new RoleNotFoundException(id);
        }
        var roleModel = new RoleModel();
        BeanUtils.copyProperties(roleDto, roleModelOpt.get());
        roleModel.setRoleId(roleModelOpt.get().getRoleId());
        return ResponseEntity.status(HttpStatus.OK).body(roleService.save(roleModel));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable(value = "id") Long id) {
        Optional<RoleModel> roleModelOpt = roleService.findById(id);
        if (roleModelOpt.isEmpty()) {
            throw new RoleNotFoundException(id);
        }
        roleService.delete(roleModelOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Role deletada com sucesso.");
    }
}
