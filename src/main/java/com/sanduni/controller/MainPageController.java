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
        System.out.print("1) Manage Guests.\t\t2) Manage Bookings.\n3) Change Credentials\nChoose Your Option : ");
        int option = new Scanner(System.in).nextInt();
        switch (option) {
            case 1->{}
            case 2->{}
            case 3->{}
            default->{
                System.out.println("Invalid Option\n");
                MainPageController.this.menu();
            }
        }
    }
}