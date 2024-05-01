package com.ruchika.hangman.services;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.model.User;
import com.ruchika.hangman.requests.LoginUserRequest;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.requests.ResetPasswordRequest;
import com.ruchika.hangman.requests.UpdateEmailRequest;

@Service
public interface IUserService {

    void saveUser(RegisterUserRequest registerUserRequest);

    String loginUser(LoginUserRequest loginUserRequest);

    User getUserProfile(String userId);

    void updateEmailOfUser(String userId, UpdateEmailRequest updateEmailRequest);

    void ResetPasswordOfUser(String userId, ResetPasswordRequest resetPasswordRequest);

    // void ForgotPasswordSendLinkViaEmail(String email);

}
