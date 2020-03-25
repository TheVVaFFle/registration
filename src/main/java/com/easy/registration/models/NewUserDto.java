package com.easy.registration.models;

import javax.validation.constraints.NotBlank;

public class NewUserDto extends UserDto {
    @NotBlank(message = "Password is required")
    private String password;
    private String matchingPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
