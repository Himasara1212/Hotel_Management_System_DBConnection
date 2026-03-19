package com.sanduni;

import com.sanduni.controller.MainPageController;
import com.sanduni.controller.UserController;

public class Main {
    public static void main(String[] args) {
        MainPageController mainPageController = new MainPageController();
        mainPageController.menu();

        UserController controller = new UserController();
        controller.showMenu();


    }
}
