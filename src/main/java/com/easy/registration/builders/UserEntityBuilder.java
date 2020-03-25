package com.easy.registration.builders;

import com.easy.registration.models.UserEntity;

public final class UserEntityBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String roles;

    private UserEntityBuilder() {
    }

    public static UserEntityBuilder anUserEntity() {
        return new UserEntityBuilder();
    }

    public UserEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntityBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEntityBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserEntityBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntityBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntityBuilder withRoles(String roles) {
        this.roles = roles;
        return this;
    }

    public UserEntity build() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setRoles(roles);
        return userEntity;
    }
}
