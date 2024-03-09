package com.ruchika.hangman.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ruchika.hangman.config.JwtService;
import com.ruchika.hangman.model.User;
import com.ruchika.hangman.responses.LoginResponse;

@Service
public class MockUserRepository implements IUserRepository{

    List<User> users = new ArrayList<User>();

    @Override
    public void saveUser(User newUser) {
        String originalPassword = newUser.getPassword();
        String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        newUser.setPassword(generatedSecuredPasswordHash);
        users.add(newUser);
    }

    @Override
    public String loginUser(String email, String password) {
        for(User user: users) {
            if(user.getEmail().equals(email)) {
                if(BCrypt.checkpw(password, user.getPassword())) {
                    System.out.println("******User logged in*******");
                    JwtService jwtService = new JwtService();
                    String token = jwtService.generateToken(user);
                    return token;
                }
                else{
                    System.out.println("******Invalid password*********");
                }
                
            }
            else{
                System.out.println("*****User not found*******");
            }
            
        }
        return null;
    }

    @Override
    public User logoutUser(String email) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User updateEmailOfUser(String userId, String email) {
        return null;
    }

    @Override
    public User updatePasswordOfUser(String userId, String password) {
        return null;
    }

    @Override
    public User sendPasswordUpdateLink(String email) {
        return null;
    }
    
}
