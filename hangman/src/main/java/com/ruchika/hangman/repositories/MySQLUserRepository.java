package com.ruchika.hangman.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.ruchika.hangman.model.User;

import lombok.extern.log4j.Log4j2;

import com.ruchika.hangman.model.RequestStatus;
import com.ruchika.hangman.model.Role;

@Repository
@Log4j2
@Primary
public class MySQLUserRepository implements IUserRepository{

    Connection connection;
    PreparedStatement statement;
    DataSource dataSource;

    public MySQLUserRepository(DataSource dataSource) throws SQLException{

        this.dataSource = dataSource;
        connection = dataSource.getConnection();
    }

    @Override
    public RequestStatus saveUser(User newUser) {
            try {
                statement = connection.prepareStatement("INSERT INTO user (userId, displayName, email, password, role, totalScore) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, newUser.getUserId());
                statement.setString(2, newUser.getDisplayName());
                statement.setString(3, newUser.getEmail());
                statement.setString(4, newUser.getPassword());
                statement.setString(5, newUser.getRole().toString());
                statement.setInt(6, newUser.getTotalScore());
                statement.executeUpdate();
                log.info("User saved successfully");
                return RequestStatus.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Error in saving user");
                throw new RuntimeException("Error in saving user");
            }
    }

    public User loginUser(String email, String password) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    if(BCrypt.checkpw(password, resultSet.getString("password"))) {
                        log.info("User logged in successfully");
                        return new User(resultSet.getString("userId"), resultSet.getString("displayName"), resultSet.getString("email"), resultSet.getString("password"), Role.valueOf(resultSet.getString("role")), resultSet.getInt("totalScore"));
                    }
                    log.error("Password does not match");
                }
                log.error("User not found");
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Error in login");
                throw new RuntimeException("Error in login");
            }
    }

    @Override
    public User getUserByEmail(String email) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    log.info("User found by email");
                    return new User(resultSet.getString("userId"), resultSet.getString("displayName"), resultSet.getString("email"), resultSet.getString("password"), Role.valueOf(resultSet.getString("role")), resultSet.getInt("totalScore"));
                }
                log.error("User not found by email");
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Error in getting user by email");
                throw new RuntimeException("Error in getting user by email");
            }
    }

    @Override
    public User getUserByUserId(String userId) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE userId = ?");
                statement.setString(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    log.info("User found by userId");
                    return new User(resultSet.getString("userId"), resultSet.getString("displayName"), resultSet.getString("email"), resultSet.getString("password"), Role.valueOf(resultSet.getString("role")), resultSet.getInt("totalScore"));
                }
                log.error("User not found by userId");
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Error in getting user by userId");
                throw new RuntimeException("Error in getting user by userId");
            }
    }

    @Override
    public RequestStatus updateEmailOfUser(String userId, String newEmail) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE userId = ?");
                statement.setString(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    statement = connection.prepareStatement("UPDATE user SET email = ? WHERE userId = ?");
                    statement.setString(1, newEmail);
                    statement.setString(2, userId);
                    statement.executeUpdate();
                }
                log.info("Email updated successfully");
                return RequestStatus.SUCCESS;
            } catch (SQLException e) {
                log.error("Error in updating email");
                e.printStackTrace();
                throw new RuntimeException("Error in updating email");
            }
    }

    @Override
    public RequestStatus ResetPasswordOfUser(String userId, String newHashedPassword) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE userId = ?");
                statement.setString(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    statement = connection.prepareStatement("UPDATE user SET password = ? WHERE userId = ?");
                    statement.setString(1, newHashedPassword);
                    statement.setString(2, userId);
                    statement.executeUpdate();
                }
                log.info("Password reset successfully");
                return RequestStatus.SUCCESS;
            } catch (SQLException e) {
                log.error("Error in resetting password");
                e.printStackTrace();
                throw new RuntimeException("Error in resetting password");
            }
            
    }

    // @Override
    // public String ForgotPasswordSendLinkViaEmail(String email) {

    //     throw new UnsupportedOperationException("Unimplemented method 'ForgotPasswordSendLinkViaEmail'");
    // }

    @Override
    public boolean checkIfEmailExists(String email) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
                return false;
            } catch (SQLException e) {
                log.error("Error in checking if email exists");
                e.printStackTrace();
                throw new RuntimeException("Error in checking if email exists");
            }
    }

    @Override
    public boolean checkIfDisplayNameExists(String displayName) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE displayName = ?");
                statement.setString(1, displayName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
                return false;
            } catch (SQLException e) {
                log.error("Error in checking if display name exists");
                e.printStackTrace();
                throw new RuntimeException("Error in checking if display name exists");
            }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
            try {
                statement = connection.prepareStatement("SELECT * FROM user");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    User user = new User(resultSet.getString("userId"), resultSet.getString("displayName"), resultSet.getString("email"), resultSet.getString("password"), Role.valueOf(resultSet.getString("role")), resultSet.getInt("totalScore"));
                    users.add(user);
                }
                log.info("All users fetched");
                return users;
            } catch (SQLException e) {
                log.error("Error in getting all users");
                e.printStackTrace();
                throw new RuntimeException("Error in getting all users");
            }
            
    }

    @Override
    public boolean checkIfPasswordMatches(String userId, String password) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE userId = ?");
                statement.setString(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    if(BCrypt.checkpw(password, resultSet.getString("password"))) {
                        return true;
                    }
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Error in checking if password matches");
                throw new RuntimeException("Error in checking if password matches");
            }
            
    }

    @Override
    public boolean checkIfUserIdExists(String userId) {
            try {
                statement = connection.prepareStatement("SELECT * FROM user WHERE userId = ?");
                statement.setString(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            return false;
            } catch (SQLException e) {
                log.error("Error in checking if userId exists");
                e.printStackTrace();
                throw new RuntimeException("Error in checking if userId exists");
            }   
    }
    
}
