package com.ruchika.hangman.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruchika.hangman.model.RequestStatus;
import com.ruchika.hangman.model.User;

@Repository
public interface IUserRepository {

    RequestStatus saveUser(User newUser);

    User loginUser(String email, String password);

    User getUserByEmail(String email);

    User getUserByUserId(String userId);

    RequestStatus updateEmailOfUser(String userId, String newEmail);

    RequestStatus ResetPasswordOfUser(String userId, String newHashedPassword);

    // String ForgotPasswordSendLinkViaEmail(String email);

    boolean checkIfEmailExists(String email);

    boolean checkIfDisplayNameExists(String displayName);

    List<User> getAllUsers();

    boolean checkIfPasswordMatches(String userId, String password);

    boolean checkIfUserIdExists(String userId);

}
