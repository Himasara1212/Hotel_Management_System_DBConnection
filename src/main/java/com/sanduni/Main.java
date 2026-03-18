package com.sanduni;

import com.sanduni.controller.MainPageController;
import com.sanduni.db.DBConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
       // Connection con = DBConnection.getInstance().getConnection();
        MainPageController mainPageController = new MainPageController();
        mainPageController.menu();
    }
}
