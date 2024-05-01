package com.ruchika.hangman.repositories;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruchika.hangman.model.User;

@Repository
public interface IUserRepository {

    void saveUser(User newUser) throws SQLException;

    User loginUser(String email, String password);

    User getUserByEmail(String email);

    User getUserByUserId(String userId);

    void updateEmailOfUser(String userId, String newEmail);

    void ResetPasswordOfUser(String userId, String newHashedPassword);

    void ForgotPasswordSendLinkViaEmail(String email);

    boolean checkIfEmailExists(String email);

    boolean checkIfDisplayNameExists(String displayName);

    List<User> getAllUsers();

    boolean checkIfPasswordMatches(String userId, String password);

    boolean checkIfUserIdExists(String userId);

}
