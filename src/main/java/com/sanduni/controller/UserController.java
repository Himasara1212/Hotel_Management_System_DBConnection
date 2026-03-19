package com.sanduni.controller;

import com.sanduni.Dto.UserDto;
import com.sanduni.Model.UserModel;
import java.sql.SQLException;
import java.util.Scanner;

public class UserController {

    private UserModel userModel;
    private Scanner scanner;

    public UserController() {
        userModel = new UserModel();
        scanner = new Scanner(System.in);
    }

    // User login with username and password
    public void loginUser() {
        System.out.println("===================================================");
        System.out.println("\t\t LOGIN PAGE");
        System.out.println("===================================================");

        System.out.print("Enter Username: ");
        String username = scanner.next();

        System.out.print("Enter Password: ");
        String password = scanner.next();

        try {
            // username and password
            UserDto user = userModel.User(username, password);

            if (user != null) {
                System.out.println("\nLogin Successful");
            } else{
                System.out.println("\nInvalid username or password");
            }
        } catch (SQLException e) {}
    }
}