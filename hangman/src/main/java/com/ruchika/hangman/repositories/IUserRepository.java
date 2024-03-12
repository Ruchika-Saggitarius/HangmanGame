package com.ruchika.hangman.repositories;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.User;

@Service
public interface IUserRepository {

    void saveUser(User newUser);

    User loginUser(String email, String password);

    User getUserByEmail(String email);

    User getUserByUserId(String userId);

    User getUserProfile(String userId);

    void updateEmailOfUser(String userId, String newEmail);

    void ResetPasswordOfUser(String userId, String oldPassword, String newPassword);

    void ForgotPasswordSendLinkViaEmail(String email);

    boolean checkIfEmailExists(String email);

    boolean checkIfDisplayNameExists(String displayName);

    List<User> getAllUsers();

    
}
