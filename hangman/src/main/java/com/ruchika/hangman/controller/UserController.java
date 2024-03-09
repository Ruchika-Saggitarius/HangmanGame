package com.ruchika.hangman.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ruchika.hangman.model.User;
import com.ruchika.hangman.repositories.IUserRepository;
import com.ruchika.hangman.requests.LoginUserRequest;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.responses.LoginResponse;

@RestController
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        userRepository.saveUser(new User(registerUserRequest.getDisplayName(), registerUserRequest.getEmail(),
                registerUserRequest.getPassword(), registerUserRequest.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest){
        String jwtToken =userRepository.loginUser(loginUserRequest.getEmail(), loginUserRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(jwtToken));

        
    }
   
}
