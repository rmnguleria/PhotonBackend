package com.expedia.web.rest.dto;

import javax.validation.constraints.NotNull;

/**
 * A DTO representing a user's credentials
 */
public class LoginDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private Boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
