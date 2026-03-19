package com.sanduni.controller;

import java.util.*;

public class MainPageController {
    public void menuTitle(){
        System.out.println("===================================================");
        System.out.println("\t\tWelcome to Hotel Management System ");
        System.out.println("===================================================");
    }

    public void menu() {
        menuTitle();
        System.out.print("1) Manage Guests.\t\t2) Manage Bookings.\n3) Change Credentials\t4) Manage User\nChoose Your Option : ");
        int option = new Scanner(System.in).nextInt();
        switch (option) {
            case 1 -> {
                GuestController guestcontroller = new GuestController();
                guestcontroller.showMenu();
            }
            case 2 -> {
                System.out.println("Manage Bookings");
            }
            case 3 -> {
                System.out.println("Change Credentials");
            }
            case 4 -> {
                UserController usercontroller = new UserController();
                usercontroller.showMenu();
            }
            default -> {
                System.out.println("Invalid Option\n");
                menu();
            }
        }
    }
}