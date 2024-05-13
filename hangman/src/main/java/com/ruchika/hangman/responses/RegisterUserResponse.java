package com.ruchika.hangman.responses;

import com.ruchika.hangman.model.User;

public class RegisterUserResponse {

    private User user;

    public RegisterUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
