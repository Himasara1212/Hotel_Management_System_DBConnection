package com.sanduni.controller;

import com.sanduni.Model.GuestModel;
import java.util.Scanner;

public class GuestController {

    private Scanner scanner;

    public GuestController() {
        scanner = new Scanner(System.in);
    }

    public void guestMenu() {
        while (true) {
            System.out.println("===================================================");
            System.out.println("\t\t GUEST MANAGEMENT SYSTEM ");
            System.out.println("===================================================");
            System.out.println("1. Add New Guest");
            System.out.println("2. View All Guests");
            System.out.println("3. Search Guest by ID");
            System.out.println("4. Update Guest");
            System.out.println("5. Delete Guest");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.next();
        }
    }
}

