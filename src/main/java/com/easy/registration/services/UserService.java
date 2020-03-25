package com.easy.registration.services;

import com.easy.registration.builders.UserEntityBuilder;
import com.easy.registration.exceptions.EmailExistsException;
import com.easy.registration.exceptions.PasswordsDontMatchException;
import com.easy.registration.models.EditUserDto;
import com.easy.registration.models.UserDto;
import com.easy.registration.models.UserEntity;
import com.easy.registration.repositories.UserRepository;
import com.easy.registration.utilities.UserUtility;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

    public UserEntity getUser(long id){
        return this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
    }

    @Transactional
    public UserEntity registerUser(UserDto userDto) throws EmailExistsException {
        if(emailExists(userDto.getEmail())){
            throw new EmailExistsException("The email address '" + userDto.getEmail() + "' already exists.");
        }

        if(!passwordsMatch(userDto.getPassword(), userDto.getMatchingPassword())){
            throw new PasswordsDontMatchException("Passwords don't match.");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(UserUtility.mapUserEntityFromRegistration(userDto));
    }

    public void editUser(EditUserDto editUserDto){
        UserEntity existingUserEntity = this.getUser(editUserDto.getId());
        userRepository.save(UserUtility.mapUserEntityFromEdit(editUserDto, existingUserEntity));
    }

    public void deleteUser(long id){
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id " + id));

        userRepository.delete(userEntity);
    }

    private boolean passwordsMatch(String password, String matchingPassword){
        return password.equals(matchingPassword);
    }

    private boolean emailExists(String email){
        Optional<UserEntity> user = this.userRepository.findByEmail(email);

        return user.isPresent();
    }
}
