package com.api.bluevelvet_music_store.repositories;

import com.api.bluevelvet_music_store.enums.RoleName;
import com.api.bluevelvet_music_store.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel,Long> {
    boolean existsByRoleName(RoleName roleName);
}
