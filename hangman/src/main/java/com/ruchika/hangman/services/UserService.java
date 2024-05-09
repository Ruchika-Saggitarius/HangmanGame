package com.ruchika.hangman.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.InvalidInputException;
import com.ruchika.hangman.exceptions.UserDoesNotExistException;
import com.ruchika.hangman.model.RequestStatus;
import com.ruchika.hangman.model.User;
import com.ruchika.hangman.repositories.IUserRepository;
import com.ruchika.hangman.requests.LoginUserRequest;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.requests.ResetPasswordRequest;
import com.ruchika.hangman.requests.UpdateEmailRequest;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public RequestStatus saveUser(RegisterUserRequest registerUserRequest) throws InvalidInputException {
        if(registerUserRequest.getEmail().isEmpty() || registerUserRequest.getPassword().isEmpty() || registerUserRequest.getDisplayName().isEmpty() || registerUserRequest.getRole() == null){
            throw new InvalidInputException("Invalid input. Please provide all the required fields.");
            }
            else if(registerUserRequest.getEmail().matches(".+@.+\\..+")==false){
                throw new InvalidInputException("Invalid email format. Please provide a valid email.");
            }
            else if(registerUserRequest.getPassword().length()<8){
                throw new InvalidInputException("Password should be atleast 8 characters long.");
            }
            else if(registerUserRequest.getDisplayName().length()<5){
                throw new InvalidInputException("Display name should be atleast 5 characters long.");
            }
            else if(registerUserRequest.getDisplayName().length()>20){
                throw new InvalidInputException("Display name should be atmost 20 characters long.");
            }
            else if(registerUserRequest.getRole().toString().equals("ADMIN")==false && registerUserRequest.getRole().toString().equals("USER")==false){
                throw new InvalidInputException("Invalid role. Please provide a valid role.");
            }
            else if(userRepository.checkIfEmailExists(registerUserRequest.getEmail())){
                throw new InvalidInputException("Email already exists. Please provide a different email.");
            }
            else if(userRepository.checkIfDisplayNameExists(registerUserRequest.getDisplayName())){
                throw new InvalidInputException("Display name already exists. Please provide a different display name.");
            }
            else {
                String userId = UUID.randomUUID().toString();
                int totalScore = 0;
                String originalPassword = registerUserRequest.getPassword();
                String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
                userRepository.saveUser(new User(userId, registerUserRequest.getDisplayName(), registerUserRequest.getEmail(),
                    generatedSecuredPasswordHash, registerUserRequest.getRole(), totalScore));
                return RequestStatus.SUCCESS;
            }
        
    }

    @Override
    public String loginUser(LoginUserRequest loginUserRequest) throws InvalidInputException{
        if(loginUserRequest.getEmail().isEmpty() || loginUserRequest.getPassword().isEmpty()){
            throw new InvalidInputException("Invalid input. Please provide all the required fields.");
        }
        else if(loginUserRequest.getEmail().matches(".+@.+\\..+")==false){
            throw new InvalidInputException("Invalid email format. Please provide a valid email.");
        }
        else {
                User user = userRepository.loginUser(loginUserRequest.getEmail(), loginUserRequest.getPassword());
                if(user == null){
                    throw new InvalidInputException("Invalid credentials. Please provide valid credentials.");
                }
                String jwtToken = jwtService.generateToken(user);
                return jwtToken;
        }
        
    }

    @Override
    public User getUserProfile(String userId) throws UserDoesNotExistException{
            boolean userIdExists = userRepository.checkIfUserIdExists(userId);
            if (!userIdExists) {
                throw new UserDoesNotExistException("User does not exist");
        }
            User user = userRepository.getUserByUserId(userId);
            return user;
    }

    @Override
    public RequestStatus updateEmailOfUser(String userId, UpdateEmailRequest updateEmailRequest) throws InvalidInputException, UserDoesNotExistException{
        boolean userIdExists = userRepository.checkIfUserIdExists(userId);
        if (!userIdExists) {
            throw new UserDoesNotExistException("User does not exist");
        }
        else if(updateEmailRequest.getNewEmail().isEmpty()){
            throw new InvalidInputException("Invalid input. Please provide all the required fields.");
        }
        else if(updateEmailRequest.getNewEmail().matches(".+@.+\\..+")==false){
            throw new InvalidInputException("Invalid email format. Please provide a valid email.");
        }
        else if(userRepository.checkIfEmailExists(updateEmailRequest.getNewEmail())){
            throw new InvalidInputException("Email already exists. Please provide a different email.");
        }
        else{
        userRepository.updateEmailOfUser(userId, updateEmailRequest.getNewEmail());
        return RequestStatus.SUCCESS;
        }
        
    }

    @Override
    public RequestStatus ResetPasswordOfUser(String userId, ResetPasswordRequest resetPasswordRequest) throws InvalidInputException, UserDoesNotExistException{
        boolean userIdExists = userRepository.checkIfUserIdExists(userId);
        if (!userIdExists) {
            throw new UserDoesNotExistException("User does not exist");
        }
        else if(resetPasswordRequest.getOldPassword().isEmpty() || resetPasswordRequest.getNewPassword().isEmpty()){
            throw new InvalidInputException("Invalid input. Please provide all the required fields.");
        }
        else if(resetPasswordRequest.getNewPassword().length()<8){
            throw new InvalidInputException("Password should be atleast 8 characters long.");
        }
        else if(resetPasswordRequest.getOldPassword().equals(resetPasswordRequest.getNewPassword())){
            throw new InvalidInputException("New password should be different from old password.");
        }
        else {
        boolean passwordMatch = userRepository.checkIfPasswordMatches(userId, resetPasswordRequest.getOldPassword());
        if(passwordMatch == false){
            throw new InvalidInputException("Invalid password. Please provide a valid password.");
        }
        else{
        String generatedSecuredPasswordHash = BCrypt.hashpw(resetPasswordRequest.getNewPassword(), BCrypt.gensalt(12));
        userRepository.ResetPasswordOfUser(userId, generatedSecuredPasswordHash);
        return RequestStatus.SUCCESS;
        }
        }
        
    }

}
