package com.ruchika.hangman.repositories;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.User;
import com.ruchika.hangman.responses.LoginResponse;

@Service
public interface IUserRepository {

    void saveUser(User newUser);

    String loginUser(String email, String password);

    User logoutUser(String email);

    User getUserByEmail(String email);

    User updateEmailOfUser(String userId, String email);

    User updatePasswordOfUser(String userId, String password);

    User sendPasswordUpdateLink(String email);

    
}
