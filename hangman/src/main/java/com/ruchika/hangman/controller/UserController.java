package com.ruchika.hangman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruchika.hangman.exceptions.BadRequestException;
import com.ruchika.hangman.exceptions.InvalidInputException;
import com.ruchika.hangman.model.User;

import com.ruchika.hangman.requests.LoginUserRequest;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.requests.ResetPasswordRequest;
import com.ruchika.hangman.requests.UpdateEmailRequest;
import com.ruchika.hangman.responses.GetUserProfileResponse;
import com.ruchika.hangman.responses.LoginResponse;
import com.ruchika.hangman.responses.RegisterUserResponse;
import com.ruchika.hangman.responses.ResetPasswordResponse;
import com.ruchika.hangman.responses.UpdateEmailOfUserResponse;
import com.ruchika.hangman.services.IUserService;
import com.ruchika.hangman.services.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    JwtService jwtService;

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        try {
            User user = userService.saveUser(registerUserRequest);
            return ResponseEntity.ok(new RegisterUserResponse(user));
        } catch (InvalidInputException e) {
            throw new BadRequestException(e.getMessage());
        }
        
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest){
        try {
            String jwtToken = userService.loginUser(loginUserRequest);
            return ResponseEntity.ok(new LoginResponse(jwtToken)); 
        } catch (InvalidInputException e) {
            throw new BadRequestException(e.getMessage());
        }
        
    }

    @GetMapping("/user/profile")
    public ResponseEntity<GetUserProfileResponse> getUserProfile(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(new GetUserProfileResponse(user.getEmail(),user.getDisplayName(), user.getRole())); 
    }

    @PostMapping("/user/email")
    public ResponseEntity<UpdateEmailOfUserResponse> updateEmailOfUser(HttpServletRequest request, @RequestBody UpdateEmailRequest updateEmailRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        try{
            User user = userService.updateEmailOfUser(userId, updateEmailRequest);
            return ResponseEntity.ok(new UpdateEmailOfUserResponse(user));
        } catch (InvalidInputException e) {
            throw new BadRequestException(e.getMessage());
        }
        
    }

    @PostMapping("/user/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPasswordOfUser(HttpServletRequest request, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((User) auth.getPrincipal()).getUserId();
        try{
            User user = userService.ResetPasswordOfUser(userId, resetPasswordRequest);
            return ResponseEntity.ok(new ResetPasswordResponse(user));
        } catch (InvalidInputException e) {
            throw new BadRequestException(e.getMessage());
        
        }
        
    }
}
