package com.sanduni.Model;

import com.sanduni.db.DBConnection;
import com.sanduni.Dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    //username and password
    public UserDto User(String username, String password) throws SQLException {
        String sql = "SELECT * FROM User WHERE name = ? AND password = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, username);
        pstm.setString(2, password);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            UserDto userDto = new UserDto();
            userDto.setUsername(resultSet.getString("name"));
            return userDto;
        }
        return null;
    }

    // All users
    public List<UserDto> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM User";

        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        List<UserDto> users = new ArrayList<>();

        while (resultSet.next()) {
            UserDto userDto = new UserDto();
            userDto.setUsername(resultSet.getString("name"));
            users.add(userDto);
        }

        return users;
    }

    // Get username
    public UserDto getUsername(String username) throws SQLException {
        String sql = "SELECT * FROM User WHERE name = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, username);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            UserDto userDto = new UserDto();
            userDto.setUsername(resultSet.getString("name"));
            return userDto;
        }
        return null;
    }

    // Check username exists
    public boolean UsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM User WHERE name = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, username);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }
        return false;
    }
}