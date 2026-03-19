package com.sanduni;

import com.sanduni.controller.MainPageController;
import com.sanduni.controller.UserController;

public class Main {
    public static void main(String[] args) {

        UserController usercontroller = new UserController();
        usercontroller.loginUser();

        MainPageController mainPageController = new MainPageController();
        mainPageController.menu();


    }
}