package com.ruchika.hangman.controller;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruchika.hangman.exceptions.BadRequestException;
import com.ruchika.hangman.model.User;
import com.ruchika.hangman.repositories.IUserRepository;
import com.ruchika.hangman.requests.LoginUserRequest;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.requests.ResetPasswordRequest;
import com.ruchika.hangman.requests.UpdateEmailRequest;
import com.ruchika.hangman.responses.GetUserProfileResponse;
import com.ruchika.hangman.responses.LoginResponse;
import com.ruchika.hangman.services.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    JwtService jwtService;

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
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
                userRepository.saveUser(new User(userId, registerUserRequest.getDisplayName(), registerUserRequest.getEmail(),
                registerUserRequest.getPassword(), registerUserRequest.getRole()));
            }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest){
        if(loginUserRequest.getEmail().isEmpty() || loginUserRequest.getPassword().isEmpty()){
            throw new BadRequestException("Invalid input. Please provide all the required fields.");
        }
        else if(loginUserRequest.getEmail().matches(".+@.+\\..+")==false){
            throw new BadRequestException("Invalid email format. Please provide a valid email.");
        }
        else {
        User user =userRepository.loginUser(loginUserRequest.getEmail(), loginUserRequest.getPassword());
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(jwtToken)); 
        }   
    }

    @GetMapping("/user/profile")
    public ResponseEntity<GetUserProfileResponse> getUserProfile(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        User user = userRepository.getUserProfile(userId);
        return ResponseEntity.ok(new GetUserProfileResponse(user.getEmail(),user.getDisplayName(), user.getRole()));        
    }

    @PostMapping("/user/email")
    public void updateEmailOfUser(HttpServletRequest request, @RequestBody UpdateEmailRequest updateEmailRequest) {
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        userRepository.updateEmailOfUser(userId, updateEmailRequest.getNewEmail());
        }
    }

    @PostMapping("/user/reset-password")
    public void resetPasswordOfUser(HttpServletRequest request, @RequestBody ResetPasswordRequest resetPasswordRequest) {
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        userRepository.ResetPasswordOfUser(userId, resetPasswordRequest.getOldPassword(), resetPasswordRequest.getNewPassword());
        }
    }
}
