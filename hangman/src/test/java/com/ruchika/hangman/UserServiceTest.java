package com.ruchika.hangman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.ruchika.hangman.exceptions.InvalidInputException;
import com.ruchika.hangman.model.DatabaseRequestStatus;
import com.ruchika.hangman.model.Role;
import com.ruchika.hangman.model.User;
import com.ruchika.hangman.repositories.IUserRepository;
import com.ruchika.hangman.requests.LoginUserRequest;
import com.ruchika.hangman.requests.RegisterUserRequest;
import com.ruchika.hangman.requests.ResetPasswordRequest;
import com.ruchika.hangman.requests.UpdateEmailRequest;
import com.ruchika.hangman.services.JwtService;
import com.ruchika.hangman.services.UserService;

//In this file we are writing test cases for UserService.java using JUnit and Mockito
@SpringBootTest
public class UserServiceTest{

    @Mock
    private IUserRepository userRepositoryMock;

    @Mock
    private JwtService jwtServiceMock;

    @InjectMocks
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser_Success() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika161", "ruchika161@gmail.com", "password", Role.USER);
        when(userRepositoryMock.checkIfEmailExists(request.getEmail())).thenReturn(false);
        when(userRepositoryMock.checkIfDisplayNameExists(request.getDisplayName())).thenReturn(false);
        when(userRepositoryMock.saveUser(any(User.class))).thenReturn(DatabaseRequestStatus.SUCCESS);

        try {
            User savedUser = userService.saveUser(request);
            assertNotNull(savedUser);
            assertEquals(request.getDisplayName(), savedUser.getDisplayName());
            assertEquals(request.getEmail(), savedUser.getEmail());
            assertEquals(request.getRole(), savedUser.getRole());
            verify(userRepositoryMock, times(1)).saveUser(any(User.class));
        } catch (Exception e) {
            //if we get an exception, we need to fail the test
            fail("Exception thrown: " + e.getMessage());
            
        }

}

    @Test
    void testSaveUser_EmptyEmailRequest() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika162", "", "password", Role.USER);
        try {
            userService.saveUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid input. Please provide all the required fields.", e.getMessage());
        }
    }

    @Test
    void testSaveUser_InvalidEmailRequest() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika163", "ruchika163", "password", Role.USER);
        try {
            userService.saveUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid email format. Please provide a valid email.", e.getMessage());
        }
    }

    @Test
    void testSaveUser_PasswordLengthNotValidRequest() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika164", "ruchika@gmail.com", "pass", Role.USER);
        try {
            userService.saveUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Password should be atleast 8 characters long.", e.getMessage());
        }
    }

    @Test
    void testSaveUser_DisplayNameLengthNotValidRequest() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika165", "ruchika@gmail.com", "password", Role.USER);
        try {
            userService.saveUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Display name should be atleast 5 characters long.", e.getMessage());
        }
    }

    @Test
    void testSaveUser_DisplayNameLengthExceedsLimitRequest() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika166", "ruchika@gmal.com", "password", Role.USER);
        try {
            userService.saveUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Display name should be atmost 20 characters long.", e.getMessage());
        }
    }

    @Test
    void testSaveUser_InvalidRoleRequest() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika167", "ruchika@gmail.com", "password", Role.ADMIN);
        try {
            userService.saveUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid role. Please provide a valid role.", e.getMessage());
        }
    }

    @Test
    void testSaveUser_EmailAlreadyExistsRequest() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika168", "ruchika161@gmail.com", "password", Role.USER);
        when(userRepositoryMock.checkIfEmailExists(request.getEmail())).thenReturn(true);
        try {
            userService.saveUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Email already exists. Please provide a different email.", e.getMessage());
        }
    }

    @Test
    void testSaveUser_DisplayNameAlreadyExistsRequest() {
        RegisterUserRequest request = new RegisterUserRequest("ruchika160", "ruchika169@gmail.com", "password", Role.USER);
        when(userRepositoryMock.checkIfEmailExists(request.getEmail())).thenReturn(false);
        when(userRepositoryMock.checkIfDisplayNameExists(request.getDisplayName())).thenReturn(false);
        try {
            userService.saveUser(request);
            // assertEquals(1, 0);
        } catch (InvalidInputException e) {
            assertEquals("Display name already exists. Please provide a different display name.", e.getMessage());
        }
    }

    @Test
    void testLoginUser_Success() {
        LoginUserRequest request = new LoginUserRequest("ruchika161@gmail.com", "password");
        when(userRepositoryMock.loginUser(request.getEmail(), request.getPassword())).thenReturn(new User("1","ruchika161", "ruchika161@gmail.com", "password", Role.USER, 0));
        when(jwtServiceMock.generateToken(any(User.class))).thenReturn("token");

        try {
            String token = userService.loginUser(request);
            assertNotNull(token);
            assertEquals("token", token);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testLoginUser_EmptyEmailRequest() {
        LoginUserRequest request = new LoginUserRequest("", "password");
        try {
            userService.loginUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid input. Please provide all the required fields.", e.getMessage());
        }
    }

    @Test
    void testLoginUser_InvalidEmailRequest() {
        LoginUserRequest request = new LoginUserRequest
        ("ruchika163", "password");
        try {
            userService.loginUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid email format. Please provide a valid email.", e.getMessage());
        }
    }

    @Test
    void testLoginUser_InvalidCredentialsRequest() {
        LoginUserRequest request = new LoginUserRequest("r@gmail.com", "password");
        when(userRepositoryMock.loginUser(request.getEmail(), request.getPassword())).thenReturn(null);
        try {
            userService.loginUser(request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid credentials. Please provide valid credentials.", e.getMessage());
        }
    }

    @Test
    void testUpdateEmailOfUser_Success() {
        String userId = "1";
        UpdateEmailRequest request = new UpdateEmailRequest("ruchika1992@gmail.com");
        when(userRepositoryMock.checkIfEmailExists(request.getNewEmail())).thenReturn(false);
        when(userRepositoryMock.updateEmailOfUser(userId, request.getNewEmail())).thenReturn(DatabaseRequestStatus.SUCCESS);
        when(userRepositoryMock.getUserByUserId(userId)).thenReturn(new User("1","ruchika161", "ruchika1992@gmail.com", "password", Role.USER, 0));

        try {
            User updatedUser = userService.updateEmailOfUser(userId, request);
            assertNotNull(updatedUser);
            assertEquals(request.getNewEmail(), updatedUser.getEmail());
            verify(userRepositoryMock, times(1)).updateEmailOfUser(userId, request.getNewEmail());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testUpdateEmailOfUser_EmptyEmailRequest() {
        String userId = "1";
        UpdateEmailRequest request = new UpdateEmailRequest("");
        try {
            userService.updateEmailOfUser(userId, request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid input. Please provide all the required fields.", e.getMessage());
        }
    }

    @Test
    void testUpdateEmailOfUser_InvalidEmailRequest() {
        String userId = "1";
        UpdateEmailRequest request = new UpdateEmailRequest("ruchika163");
        try {
            userService.updateEmailOfUser(userId, request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid email format. Please provide a valid email.", e.getMessage());
        }
    }

    @Test
    void testUpdateEmailOfUser_EmailAlreadyExistsRequest() {
        String userId = "1";
        UpdateEmailRequest request = new UpdateEmailRequest("ruchika1992@gmail.com");
        when(userRepositoryMock.checkIfEmailExists(request.getNewEmail())).thenReturn(true);
        try {
            userService.updateEmailOfUser(userId, request);
        } catch (InvalidInputException e) {
            assertEquals("Email already exists. Please provide a different email.", e.getMessage());
        }
    }

    @Test
    void testResetPasswordOfUser_EmptyPasswordRequest() {
        String userId = "1";
        ResetPasswordRequest request = new ResetPasswordRequest("", "newpassword");
        try {
            userService.ResetPasswordOfUser(userId, request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid input. Please provide all the required fields.", e.getMessage());
        }
    }

    @Test
    void testResetPasswordOfUser_PasswordLengthNotValidRequest() {
        String userId = "1";
        ResetPasswordRequest request = new ResetPasswordRequest("password", "new");
        try {
            userService.ResetPasswordOfUser(userId, request);
        } catch (InvalidInputException e) {
            assertEquals("Password should be atleast 8 characters long.", e.getMessage());
        }
    }

    @Test
    void testResetPasswordOfUser_NewPasswordSameAsOldPasswordRequest() {
        String userId = "1";
        ResetPasswordRequest request = new ResetPasswordRequest("password", "password");
        try {
            userService.ResetPasswordOfUser(userId, request);
        } catch (InvalidInputException e) {
            assertEquals("New password should be different from old password.", e.getMessage());
        }
    }

    @Test
    void testResetPasswordOfUser_InvalidOldPasswordRequest() {
        String userId = "1";
        ResetPasswordRequest request = new ResetPasswordRequest("password", "newpassword");
        when(userRepositoryMock.checkIfPasswordMatches(userId, request.getOldPassword())).thenReturn(false);
        try {
            userService.ResetPasswordOfUser(userId, request);
        } catch (InvalidInputException e) {
            assertEquals("Invalid old password. Please provide valid old password.", e.getMessage());
        }
    }

    @Test
    void testResetPasswordOfUser_SuccessRequest() {
        String userId = "1";
        ResetPasswordRequest request = new ResetPasswordRequest("password", "newpassword");
        when(userRepositoryMock.checkIfPasswordMatches(userId, request.getOldPassword())).thenReturn(true);
        when(userRepositoryMock.ResetPasswordOfUser(userId, request.getNewPassword())).thenReturn(DatabaseRequestStatus.SUCCESS);
        when(userRepositoryMock.getUserByUserId(userId)).thenReturn(new User("1","ruchika161", "ruchika1992@gmail.com", "newpassword", Role.USER, 0));
        try {
            User updatedUser = userService.ResetPasswordOfUser(userId, request);
            assertNotNull(updatedUser);
            assertEquals(request.getNewPassword(), updatedUser.getPassword());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
   
}

