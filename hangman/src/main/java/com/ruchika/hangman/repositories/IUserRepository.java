package com.ruchika.hangman.repositories;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.User;

@Service
public interface IUserRepository {

    void saveUser(User newUser);

    void loginUser(String email, String password);

    User logoutUser(String email);

    User getUserByUserId(String userId);

    User updateEmailOfUser(String userId, String email);

    User updatePasswordOfUser(String userId, String password);

    User sendPasswordUpdateLink(String email);
    
}
