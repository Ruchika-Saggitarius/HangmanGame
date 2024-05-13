package com.ruchika.hangman.services;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.InvalidInputException;
import com.ruchika.hangman.model.User;
import com.ruchika.hangman.requests.LoginUserRequest;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.requests.ResetPasswordRequest;
import com.ruchika.hangman.requests.UpdateEmailRequest;

@Service
public interface IUserService {

    User saveUser(RegisterUserRequest registerUserRequest) throws InvalidInputException;

    String loginUser(LoginUserRequest loginUserRequest) throws InvalidInputException;

    User getUserProfile(String userId);

    User updateEmailOfUser(String userId, UpdateEmailRequest updateEmailRequest) throws InvalidInputException;

    User ResetPasswordOfUser(String userId, ResetPasswordRequest resetPasswordRequest) throws InvalidInputException;

    // void ForgotPasswordSendLinkViaEmail(String email);

}
