package com.ruchika.hangman.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ruchika.hangman.exceptions.BadRequestException;
import com.ruchika.hangman.model.User;

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
    public User loginUser(String email, String password) {
        for(User user: users) {
            if(user.getEmail().equals(email)) {
                if(BCrypt.checkpw(password, user.getPassword())) {
                    return user;
                }
                else{
                throw new BadRequestException("Invalid Password. Please provide a valid password.");
                } 
            }
        }
        throw new BadRequestException("Invalid Email. Please provide a valid email.");
    }

    @Override
    public User getUserByEmail(String email) {
        for(User user: users) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void updateEmailOfUser(String userId, String newEmail) {
        for(User user: users) {
            if(user.getUserId().equals(userId)) {
                user.setEmail(newEmail);
            }
        }
    }

    @Override
    public User getUserProfile(String userId) {
        for(User user: users) {
            if(user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByUserId(String userId) {
        for(User user: users) {
            if(user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
        
    }

    @Override
    public void ResetPasswordOfUser(String userId, String oldPassword, String newPassword) {
        for(User user: users) {
            if(user.getUserId().equals(userId)) {
                if(BCrypt.checkpw(oldPassword, user.getPassword())) {
                    String generatedSecuredPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
                    user.setPassword(generatedSecuredPasswordHash);
                }
                else{
                    throw new BadRequestException("Invalid Password. Please provide a valid password.");
                    }
            }
        throw new BadRequestException("Invalid Email. Please provide a valid email.");
    }
        
    }

    @Override
    public void ForgotPasswordSendLinkViaEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ForgotPasswordSendLinkViaEmail'");
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        for(User user: users) {
            if(user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIfDisplayNameExists(String displayName) {
        for(User user: users) {
            if(user.getDisplayName().equals(displayName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

  

    
}
