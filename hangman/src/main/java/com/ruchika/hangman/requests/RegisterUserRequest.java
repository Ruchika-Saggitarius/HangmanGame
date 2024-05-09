package com.ruchika.hangman.requests;

import com.ruchika.hangman.model.Role;

public class RegisterUserRequest {

    private String displayName;
    private String email;
    private String password;
    private Role role;

    public RegisterUserRequest() {
    }

    public RegisterUserRequest(String displayName, String email, String password, Role role) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    
}
