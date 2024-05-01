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
import com.ruchika.hangman.model.Role;

@Repository
@Primary

public class MySQLUserRepository implements IUserRepository{

    Connection connection;
    PreparedStatement statement;
    DataSource dataSource;

    public MySQLUserRepository(DataSource dataSource) throws SQLException {

        this.dataSource = dataSource;
        connection = dataSource.getConnection();
    }

    @Override
    public void saveUser(User newUser) {
        try {
            statement = connection.prepareStatement("INSERT INTO user (userId, displayName, email, password, role, totalScore) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, newUser.getUserId());
            statement.setString(2, newUser.getDisplayName());
            statement.setString(3, newUser.getEmail());
            statement.setString(4, newUser.getPassword());
            statement.setString(5, newUser.getRole().toString());
            statement.setInt(6, newUser.getTotalScore());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } 
        }
    }

    public User loginUser(String email, String password) {
        try {
            statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if(BCrypt.checkpw(password, resultSet.getString("password"))) {
                    return new User(resultSet.getString("userId"), resultSet.getString("displayName"), resultSet.getString("email"), resultSet.getString("password"), Role.valueOf(resultSet.getString("role")), resultSet.getInt("totalScore"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("userId"), resultSet.getString("displayName"), resultSet.getString("email"), resultSet.getString("password"), Role.valueOf(resultSet.getString("role")), resultSet.getInt("totalScore"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User getUserByUserId(String userId) {
        try {
            statement = connection.prepareStatement("SELECT * FROM user WHERE userId = ?");
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("userId"), resultSet.getString("displayName"), resultSet.getString("email"), resultSet.getString("password"), Role.valueOf(resultSet.getString("role")), resultSet.getInt("totalScore"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void updateEmailOfUser(String userId, String newEmail) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void ResetPasswordOfUser(String userId, String newHashedPassword) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void ForgotPasswordSendLinkViaEmail(String email) {

        throw new UnsupportedOperationException("Unimplemented method 'ForgotPasswordSendLinkViaEmail'");
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        try {
            statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
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
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
}
