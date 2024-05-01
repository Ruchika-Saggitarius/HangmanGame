package com.ruchika.hangman.services;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.BadRequestException;
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
    public void saveUser(RegisterUserRequest registerUserRequest) {
        if(registerUserRequest.getEmail().isEmpty() || registerUserRequest.getPassword().isEmpty() || registerUserRequest.getDisplayName().isEmpty() || registerUserRequest.getRole() == null){
            throw new BadRequestException("Invalid input. Please provide all the required fields.");
            }
            else if(registerUserRequest.getEmail().matches(".+@.+\\..+")==false){
                throw new BadRequestException("Invalid email format. Please provide a valid email.");
            }
            else if(registerUserRequest.getPassword().length()<8){
                throw new BadRequestException("Password should be atleast 8 characters long.");
            }
            else if(registerUserRequest.getDisplayName().length()<5){
                throw new BadRequestException("Display name should be atleast 5 characters long.");
            }
            else if(registerUserRequest.getDisplayName().length()>20){
                throw new BadRequestException("Display name should be atmost 20 characters long.");
            }
            else if(registerUserRequest.getRole().toString().equals("ADMIN")==false && registerUserRequest.getRole().toString().equals("USER")==false){
                throw new BadRequestException("Invalid role. Please provide a valid role.");
            }
            else if(userRepository.checkIfEmailExists(registerUserRequest.getEmail())){
                throw new BadRequestException("Email already exists. Please provide a different email.");
            }
            else if(userRepository.checkIfDisplayNameExists(registerUserRequest.getDisplayName())){
                throw new BadRequestException("Display name already exists. Please provide a different display name.");
            }
            else {
                String userId = UUID.randomUUID().toString();
                int totalScore = 0;
                String originalPassword = registerUserRequest.getPassword();
                String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
                try {
                    userRepository.saveUser(new User(userId, registerUserRequest.getDisplayName(), registerUserRequest.getEmail(),
                    generatedSecuredPasswordHash, registerUserRequest.getRole(), totalScore));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public String loginUser(LoginUserRequest loginUserRequest) {
        if(loginUserRequest.getEmail().isEmpty() || loginUserRequest.getPassword().isEmpty()){
            throw new BadRequestException("Invalid input. Please provide all the required fields.");
        }
        else if(loginUserRequest.getEmail().matches(".+@.+\\..+")==false){
            throw new BadRequestException("Invalid email format. Please provide a valid email.");
        }
        else {
        User user = userRepository.loginUser(loginUserRequest.getEmail(), loginUserRequest.getPassword());
        if(user == null){
            throw new BadRequestException("Invalid credentials. Please provide valid credentials.");
        }
        String jwtToken = jwtService.generateToken(user);
        return jwtToken;
        }   
    }

    @Override
    public User getUserProfile(String userId) {
        boolean userExist = userRepository.checkIfUserIdExists(userId);
        if(userExist == false){
            throw new BadRequestException("Invalid userId. Please provide a valid userId.");
        }
        User user = userRepository.getUserByUserId(userId);
        if(user == null){
            throw new BadRequestException("Invalid userId. Please provide a valid userId.");
        }
        return user;
    }

    @Override
    public void updateEmailOfUser(String userId, UpdateEmailRequest updateEmailRequest) {
        if(updateEmailRequest.getNewEmail().isEmpty()){
            throw new BadRequestException("Invalid input. Please provide all the required fields.");
        }
        else if(updateEmailRequest.getNewEmail().matches(".+@.+\\..+")==false){
            throw new BadRequestException("Invalid email format. Please provide a valid email.");
        }
        else if(userRepository.checkIfEmailExists(updateEmailRequest.getNewEmail())){
            throw new BadRequestException("Email already exists. Please provide a different email.");
        }
        else{
        userRepository.updateEmailOfUser(userId, updateEmailRequest.getNewEmail());
        }
    }

    @Override
    public void ResetPasswordOfUser(String userId, ResetPasswordRequest resetPasswordRequest) {
        if(resetPasswordRequest.getOldPassword().isEmpty() || resetPasswordRequest.getNewPassword().isEmpty()){
            throw new BadRequestException("Invalid input. Please provide all the required fields.");
        }
        else if(resetPasswordRequest.getNewPassword().length()<8){
            throw new BadRequestException("Password should be atleast 8 characters long.");
        }
        else if(resetPasswordRequest.getOldPassword().equals(resetPasswordRequest.getNewPassword())){
            throw new BadRequestException("New password should be different from old password.");
        }
        else {
        boolean userExist = userRepository.checkIfUserIdExists(userId);
        if(userExist == false){
            throw new BadRequestException("Invalid userId. Please provide a valid userId.");
        }
        boolean passwordMatch = userRepository.checkIfPasswordMatches(userId, resetPasswordRequest.getOldPassword());
        if(passwordMatch == false){
            throw new BadRequestException("Invalid password. Please provide a valid password.");
        }
        else{
        String generatedSecuredPasswordHash = BCrypt.hashpw(resetPasswordRequest.getNewPassword(), BCrypt.gensalt(12));
        userRepository.ResetPasswordOfUser(userId, generatedSecuredPasswordHash);
        };
        }
    }

}
