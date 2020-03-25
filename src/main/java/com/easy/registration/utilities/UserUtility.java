package com.easy.registration.utilities;

import com.easy.registration.builders.UserEntityBuilder;
import com.easy.registration.models.NewUserDto;
import com.easy.registration.models.UserDto;
import com.easy.registration.models.UserEntity;

public class UserUtility {
    public static UserEntity mapUserEntity(UserDto userDto){
        UserEntityBuilder userEntityBuilder = UserEntityBuilder.anUserEntity();

        userEntityBuilder.withFirstName(userDto.getFirstName())
                .withLastName(userDto.getLastName())
                .withEmail(userDto.getEmail())
                .withRoles("ROLE_USER");

        if(userDto instanceof NewUserDto){
            NewUserDto newUserDto = (NewUserDto) userDto;
            userEntityBuilder.withPassword(newUserDto.getPassword());
        }

        return userEntityBuilder.build();
    }
}
