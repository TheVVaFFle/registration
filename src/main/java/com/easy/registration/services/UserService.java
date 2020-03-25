package com.easy.registration.services;

import com.easy.registration.exceptions.EmailExistsException;
import com.easy.registration.exceptions.PasswordsDontMatchException;
import com.easy.registration.models.UserDto;
import com.easy.registration.models.NewUserDto;
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
    public UserEntity registerUser(NewUserDto newUserDto) throws EmailExistsException {
        if(emailExists(newUserDto.getEmail())){
            throw new EmailExistsException("The email address '" + newUserDto.getEmail() + "' already exists.");
        }

        if(!passwordsMatch(newUserDto.getPassword(), newUserDto.getMatchingPassword())){
            throw new PasswordsDontMatchException("Passwords don't match.");
        }

        newUserDto.setPassword(passwordEncoder.encode(newUserDto.getPassword()));

        return userRepository.save(UserUtility.mapUserEntity(newUserDto));
    }

    public void editUser(UserDto userDto){
        UserEntity userEntity = UserUtility.mapUserEntity(userDto);
        userEntity.setId(userDto.getId());

        userRepository.save(userEntity);
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
