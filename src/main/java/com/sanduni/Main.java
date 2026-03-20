package com.sanduni;

import com.sanduni.controller.MainPageController;
import com.sanduni.controller.UserController;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserController usercontroller = new UserController();
        usercontroller.loginUser();

        MainPageController mainPageController = new MainPageController();
        mainPageController.menu();
    }
}