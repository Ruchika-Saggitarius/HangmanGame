package com.ruchika.hangman.services;

import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.InvalidInputException;
import com.ruchika.hangman.exceptions.UserDoesNotExistException;
import com.ruchika.hangman.model.RequestStatus;
import com.ruchika.hangman.model.User;
import com.ruchika.hangman.requests.LoginUserRequest;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.requests.ResetPasswordRequest;
import com.ruchika.hangman.requests.UpdateEmailRequest;

@Service
public interface IUserService {

    RequestStatus saveUser(RegisterUserRequest registerUserRequest) throws InvalidInputException;

    String loginUser(LoginUserRequest loginUserRequest) throws InvalidInputException;

    User getUserProfile(String userId) throws UserDoesNotExistException;

    RequestStatus updateEmailOfUser(String userId, UpdateEmailRequest updateEmailRequest) throws InvalidInputException, UserDoesNotExistException;

    RequestStatus ResetPasswordOfUser(String userId, ResetPasswordRequest resetPasswordRequest) throws InvalidInputException, UserDoesNotExistException;

    // void ForgotPasswordSendLinkViaEmail(String email);

}
