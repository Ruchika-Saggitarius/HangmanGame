package com.ruchika.hangman;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ruchika.hangman.model.Role;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.services.IUserService;

//In this file we are writing test cases for UserService.java using JUnit and Mockito
@SpringBootTest
public class UserServiceTest{

    @Autowired
    private IUserService userService;

    
}