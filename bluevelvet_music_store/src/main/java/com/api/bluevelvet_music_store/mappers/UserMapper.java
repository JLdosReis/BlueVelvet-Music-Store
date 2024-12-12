package com.api.bluevelvet_music_store.mappers;

import com.api.bluevelvet_music_store.dtos.UserDto;
import com.api.bluevelvet_music_store.dtos.RoleDto;
import com.api.bluevelvet_music_store.models.UserModel;
import com.api.bluevelvet_music_store.models.RoleModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserModel toModel(UserDto userDto) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel, "userPhoto","roles");
        userModel.setUserPhoto(userDto.userPhoto());
        userModel.setRoles(mapRoles(userDto.roles()));
        return userModel;
    }

    private List<RoleModel> mapRoles(@NotNull List<RoleDto> roleDtos) {
        return roleDtos.stream()
                .map(roleDto -> {
                    RoleModel role = new RoleModel();
                    role.setRoleName(roleDto.roleName());
                    role.setDescription(roleDto.description());
                    return role;
                })
                .collect(Collectors.toList());
    }

}
