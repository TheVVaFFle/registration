package com.easy.registration.utilities;

import com.easy.registration.builders.UserEntityBuilder;
import com.easy.registration.models.EditUserDto;
import com.easy.registration.models.UserDto;
import com.easy.registration.models.UserEntity;

public class UserUtility {
    public static UserEntity mapUserEntityFromRegistration(UserDto userDto){
        return UserEntityBuilder.anUserEntity().withId(userDto.getId())
                .withFirstName(userDto.getFirstName())
                .withLastName(userDto.getLastName())
                .withEmail(userDto.getEmail())
                .withPassword(userDto.getPassword())
                .withRoles("ROLE_USER")
                .build();
    }

    public static UserEntity mapUserEntityFromEdit(EditUserDto editUserDto, UserEntity existingUserEntity){
        return UserEntityBuilder.anUserEntity()
                .withId(editUserDto.getId())
                .withFirstName(editUserDto.getFirstName())
                .withLastName(editUserDto.getLastName())
                .withEmail(editUserDto.getEmail())
                .withPassword(existingUserEntity.getPassword())
                .withRoles(existingUserEntity.getRoles())
                .build();
    }
}
