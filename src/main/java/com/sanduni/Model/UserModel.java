package com.sanduni.Model;

import com.sanduni.db.DBConnection;
import com.sanduni.Dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    // Save user to database
    public boolean saveUser(UserDto userDto) throws SQLException {
        String sql = "INSERT INTO User (user_id, name, email, password) VALUES (?, ?, ?, ?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userDto.getId());
        pstm.setString(2, userDto.getUsername());
        pstm.setString(3, userDto.getEmail());
        pstm.setString(4, userDto.getPassword());

        return pstm.executeUpdate() > 0;
    }

    // Update user in database
    public boolean updateUser(UserDto userDto) throws SQLException {
        String sql = "UPDATE User SET name = ?, email = ?, password = ? WHERE user_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userDto.getUsername());  // username from DTO maps to name in table
        pstm.setString(2, userDto.getEmail());
        pstm.setString(3, userDto.getPassword());
        pstm.setString(4, userDto.getId());

        return pstm.executeUpdate() > 0;
    }

    // Delete user from database
    public boolean deleteUser(String userId) throws SQLException {
        String sql = "DELETE FROM User WHERE user_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userId);

        return pstm.executeUpdate() > 0;
    }

    // Get user by ID
    public UserDto getUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM User WHERE user_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new UserDto(
                    resultSet.getString("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }
        return null;
    }

    // Get user by email
    public UserDto getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM User WHERE email = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, email);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new UserDto(
                    resultSet.getString("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }
        return null;
    }

    // Get all users
    public List<UserDto> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM User";

        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        List<UserDto> users = new ArrayList<>();

        while (resultSet.next()) {
            UserDto userDto = new UserDto(
                    resultSet.getString("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
            users.add(userDto);
        }

        return users;
    }

    // Authenticate user
    public UserDto authenticateUser(String email, String password) throws SQLException {
        String sql = "SELECT * FROM User WHERE email = ? AND password = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, email);
        pstm.setString(2, password);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new UserDto(
                    resultSet.getString("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }
        return null;
    }

    // Check if user exists by email
    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, email);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }
        return false;
    }

    // Generate new user ID
    public String generateNewUserId() throws SQLException {
        String sql = "SELECT user_id FROM User ORDER BY user_id DESC LIMIT 1";

        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            String lastId = resultSet.getString("user_id");
            int number = Integer.parseInt(lastId.substring(1));
            return String.format("U%03d", number + 1);
        }

        return "U001";
    }
}