package com.easy.registration.services;

import com.easy.registration.models.UserEntity;
import com.easy.registration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if(!userEntity.isPresent()){
            throw new UsernameNotFoundException("No user found with email: '" + email + "'");
        }

        return getUserDetails(userEntity.get());
    }

    public UserDetails getUserDetails(UserEntity userEntity){
        boolean enabled = true,
                accountNonExpired = true,
                credentialsNonExpired = true,
                accountNonLocked = true;

        return new User(
            userEntity.getEmail(),
            userEntity.getPassword().toLowerCase(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            getAuthorities(userEntity.getRoles())
        );
    }

    private static List<GrantedAuthority> getAuthorities (String roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles.split("\\s+")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }
}
