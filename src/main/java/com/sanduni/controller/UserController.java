package com.sanduni.controller;

import com.sanduni.Dto.UserDto;
import com.sanduni.Model.UserModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserController {

    private UserModel userModel;
    private Scanner scanner;

    public UserController() {
        userModel = new UserModel();
        scanner = new Scanner(System.in);
    }

    // Main menu
    public void showMenu() {
        while (true) {
            System.out.println("===================================================");
            System.out.println("\t\t USER MANAGEMENT SYSTEM ");
            System.out.println("===================================================");
            System.out.println("1. Add New User");
            System.out.println("2. View All Users");
            System.out.println("3. Search User by ID");
            System.out.println("4. Search User by Email");
            System.out.println("5. Update User");
            System.out.println("6. Delete User");
            System.out.println("7. User Login");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        viewAllUsers();
                        break;
                    case 3:
                        searchUserById();
                        break;
                    case 4:
                        searchUserByEmail();
                        break;
                    case 5:
                        updateUser();
                        break;
                    case 6:
                        deleteUser();
                        break;
                    case 7:
                        loginUser();
                        break;
                    case 8:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    // Add new user
    private void addUser() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t ADD NEW USER ");
        System.out.println("===================================================");

        // Generate new user ID
        String userId = userModel.generateNewUserId();
        System.out.println("Generated User ID: " + userId);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        // Check if email already exists
        if (userModel.isEmailExists(email)) {
            System.out.println("Email already exists!");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        UserDto userDto = new UserDto(userId, name, email, password);

        boolean saved = userModel.saveUser(userDto);

        if (saved) {
            System.out.println("User saved successfully!");
            System.out.println("User ID: " + userId);
        } else {
            System.out.println("Failed to save user!");
        }
    }

    // View all users
    private void viewAllUsers() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t ALL USERS ");
        System.out.println("===================================================");

        List<UserDto> users = userModel.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found!");
            return;
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("%-8s %-15s %-20s %-10s\n", "ID", "Name", "Email", "Password");
        System.out.println("----------------------------------------------------------");

        for (UserDto user : users) {
            System.out.printf("%-8s %-15s %-20s %-10s\n",
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword()
            );
        }
        System.out.println("---------------------------------------------------------");
    }

    // Search user by ID
    private void searchUserById() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t SEARCH USER BY ID ");
        System.out.println("===================================================");

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        UserDto user = userModel.getUserById(userId);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        displayUserDetails(user);
    }

    // Search user by Email
    private void searchUserByEmail() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t SEARCH USER BY EMAIL ");
        System.out.println("===================================================");

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        UserDto user = userModel.getUserByEmail(email);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        displayUserDetails(user);
    }

    // Update user
    private void updateUser() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t UPDATE USER ");
        System.out.println("====================================================");

        System.out.print("Enter User ID to update: ");
        String userId = scanner.nextLine();

        // Check if user exists
        UserDto existingUser = userModel.getUserById(userId);

        if (existingUser == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("Current User Details:");
        displayUserDetails(existingUser);

        System.out.println("\nEnter new details (press Enter to keep current value):");

        System.out.print("Enter Name [" + existingUser.getUsername() + "]: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            name = existingUser.getUsername();
        }

        System.out.print("Enter Email [" + existingUser.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) {
            email = existingUser.getEmail();
        } else {
            // Check if new email already exists (and it's not the current user's email)
            if (!email.equals(existingUser.getEmail()) && userModel.isEmailExists(email)) {
                System.out.println("Error: Email already exists!");
                return;
            }
        }

        System.out.print("Enter Password (press Enter to keep current): ");
        String password = scanner.nextLine();
        if (password.trim().isEmpty()) {
            password = existingUser.getPassword();
        }

        UserDto updatedUser = new UserDto(userId, name, email, password);

        boolean updated = userModel.updateUser(updatedUser);

        if (updated) {
            System.out.println("User updated successfully!");
        } else {
            System.out.println("Failed to update user!");
        }
    }

    // Delete user
    private void deleteUser() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t DELETE USER ");
        System.out.println("===================================================");

        System.out.print("Enter User ID to delete: ");
        String userId = scanner.nextLine();

        // Check if user exists
        UserDto user = userModel.getUserById(userId);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("User to delete:");
        displayUserDetails(user);

        System.out.print("Are you sure you want to delete this user? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            boolean deleted = userModel.deleteUser(userId);

            if (deleted) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Failed to delete user!");
            }
        } else {
            System.out.println("Delete cancelled!");
        }
    }

    // User login
    private void loginUser() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t USER LOGIN ");
        System.out.println("===================================================");

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        UserDto user = userModel.authenticateUser(email, password);

        if (user != null) {
            System.out.println("Login successful!");
            System.out.println("Welcome " + user.getUsername() + "!");
        } else {
            System.out.println("Login failed! Invalid email or password!");
        }
    }

    // Display user details
    private void displayUserDetails(UserDto user) {
        System.out.println("===================================================");
        System.out.println("\t\t USER DETAILS ");
        System.out.println("===================================================");
        System.out.println("User ID: " + user.getId());
        System.out.println("Name: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());

    }
}