package com.sanduni.controller;

import com.sanduni.Dto.GuestDto;
import com.sanduni.Model.GuestModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GuestController {

    private GuestModel guestModel;
    private Scanner scanner;

    public GuestController() {
        guestModel = new GuestModel();
        scanner = new Scanner(System.in);
    }

    // Main menu
    public void showMenu() {
        while (true) {
            System.out.println("===================================================");
            System.out.println("\t\t GUEST MANAGEMENT SYSTEM ");
            System.out.println("===================================================");
            System.out.println("1. Add New Guest");
            System.out.println("2. View All Guests");
            System.out.println("3. Search Guest by ID");
            System.out.println("4. Search Guest by Email");
            System.out.println("5. Search Guest by NIC");
            System.out.println("6. Search Guests (General Search)");
            System.out.println("7. Update Guest");
            System.out.println("8. Delete Guest");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        addGuest();
                        break;
                    case 2:
                        viewAllGuests();
                        break;
                    case 3:
                        searchGuestById();
                        break;
                    case 4:
                        searchGuestByEmail();
                        break;
                    case 5:
                        searchGuestByNic();
                        break;
                    case 6:
                        searchGuests();
                        break;
                    case 7:
                        updateGuest();
                        break;
                    case 8:
                        deleteGuest();
                        break;
                    case 9:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Add new guest
    private void addGuest() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t ADD NEW GUEST ");
        System.out.println("===================================================");

        // Generate new guest ID
        String guestId = guestModel.getNextGuestId();
        System.out.println("Generated Guest ID: " + guestId);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Country: ");
        String country = scanner.nextLine();

        System.out.print("Enter NIC OR PASSPORT: ");
        String nic = scanner.nextLine();

        // Check if NIC already exists
        if (guestModel.isNicExists(nic)) {
            System.out.println("Error: NIC already exists!");
            return;
        }

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        // Check if email already exists
        if (guestModel.isEmailExists(email)) {
            System.out.println("Error: Email already exists!");
            return;
        }

        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();

        System.out.print("Enter Number of Members: ");
        int memberCount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        GuestDto guestDto = new GuestDto(guestId, name, address, country, nic, email, contact, memberCount);

        boolean saved = guestModel.saveGuest(guestDto);

        if (saved) {
            System.out.println("Guest saved successfully!");
            System.out.println("Guest ID: " + guestId);
        } else {
            System.out.println("Failed to save guest!");
        }
    }

    // View all guests
    private void viewAllGuests() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t ALL GUESTS ");
        System.out.println("===================================================");

        List<GuestDto> guests = guestModel.getAllGuests();

        if (guests.isEmpty()) {
            System.out.println("No guests found!");
            return;
        }

        displayGuestTable(guests);
        System.out.println("\nTotal Guests: " + guests.size());
    }

    // Search guest by ID
    private void searchGuestById() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t SEARCH GUEST ID ");
        System.out.println("===================================================");

        System.out.print("Enter Guest ID: ");
        String guestId = scanner.nextLine();

        GuestDto guest = guestModel.getGuest(guestId);

        if (guest == null) {
            System.out.println("Guest not found!");
            return;
        }

        displayGuestDetails(guest);
    }

    // Search guest by Email
    private void searchGuestByEmail() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t SEARCH GUEST EMAIL ");
        System.out.println("===================================================");

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        GuestDto guest = guestModel.getGuestByEmail(email);

        if (guest == null) {
            System.out.println("Guest not found!");
            return;
        }

        displayGuestDetails(guest);
    }

    // Search guest by NIC
    private void searchGuestByNic() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t SEARCH GUEST NIC OR PASSPORT");
        System.out.println("===================================================");

        System.out.print("Enter NIC or Passport Number: ");
        String nicOrPassport = scanner.nextLine();

        GuestDto guest = guestModel.getGuestByNic(nicOrPassport);

        if (guest == null) {
            System.out.println("Guest not found!");
            return;
        }

        displayGuestDetails(guest);
    }

    // General search
    private void searchGuests() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t SEARCH GUESTS ");
        System.out.println("===================================================");

        System.out.print("Enter search term (ID, Name, NIC, Email, or Contact): ");
        String searchTerm = scanner.nextLine();

        List<GuestDto> guests = guestModel.searchGuests(searchTerm);

        if (guests.isEmpty()) {
            System.out.println("No guests found matching '" + searchTerm + "'");
            return;
        }

        System.out.println("\nFound " + guests.size() + " guest(s):");
        displayGuestTable(guests);
    }

    // Update guest
    private void updateGuest() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t UPDATE GUEST ");
        System.out.println("===================================================");

        System.out.print("Enter Guest ID to update: ");
        String guestId = scanner.nextLine();

        // Check if guest exists
        GuestDto existingGuest = guestModel.getGuest(guestId);

        if (existingGuest == null) {
            System.out.println("Guest not found!");
            return;
        }

        System.out.println("\nCurrent Guest Details:");
        displayGuestDetails(existingGuest);

        System.out.println("\nEnter new details (press Enter to keep current value):");

        System.out.print("Enter Name [" + existingGuest.getName() + "]: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            name = existingGuest.getName();
        }

        System.out.print("Enter Address [" + existingGuest.getAddress() + "]: ");
        String address = scanner.nextLine();
        if (address.trim().isEmpty()) {
            address = existingGuest.getAddress();
        }

        System.out.print("Enter Country [" + existingGuest.getCountry() + "]: ");
        String country = scanner.nextLine();
        if (country.trim().isEmpty()) {
            country = existingGuest.getCountry();
        }

        System.out.print("Enter NIC [" + existingGuest.getNic() + "]: ");
        String nic = scanner.nextLine();
        if (nic.trim().isEmpty()) {
            nic = existingGuest.getNic();
        } else {
            // Check if new NIC already exists (and it's not the current guest's NIC)
            if (!nic.equals(existingGuest.getNic()) && guestModel.isNicExists(nic)) {
                System.out.println("Error: NIC already exists!");
                return;
            }
        }

        System.out.print("Enter Email [" + existingGuest.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) {
            email = existingGuest.getEmail();
        } else {
            // Check if new email already exists (and it's not the current guest's email)
            if (!email.equals(existingGuest.getEmail()) && guestModel.isEmailExists(email)) {
                System.out.println("Error: Email already exists!");
                return;
            }
        }

        System.out.print("Enter Contact [" + existingGuest.getContact() + "]: ");
        String contact = scanner.nextLine();
        if (contact.trim().isEmpty()) {
            contact = existingGuest.getContact();
        }

        System.out.print("Enter Member Count [" + existingGuest.getMemberCount() + "]: ");
        String memberCountStr = scanner.nextLine();
        int memberCount;
        if (memberCountStr.trim().isEmpty()) {
            memberCount = existingGuest.getMemberCount();
        } else {
            memberCount = Integer.parseInt(memberCountStr);
        }

        GuestDto updatedGuest = new GuestDto(guestId, name, address, country, nic, email, contact, memberCount);

        boolean updated = guestModel.updateGuest(updatedGuest);

        if (updated) {
            System.out.println("Guest updated successfully!");
        } else {
            System.out.println("Failed to update guest!");
        }
    }

    // Delete guest
    private void deleteGuest() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t DELETE GUEST ");
        System.out.println("===================================================");

        System.out.print("Enter Guest ID to delete: ");
        String guestId = scanner.nextLine();

        // Check if guest exists
        GuestDto guest = guestModel.getGuest(guestId);

        if (guest == null) {
            System.out.println("Guest not found!");
            return;
        }

        System.out.println("\nGuest to delete:");
        displayGuestDetails(guest);

        System.out.print("\nAre you sure you want to delete this guest? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            boolean deleted = guestModel.deleteGuest(guestId);

            if (deleted) {
                System.out.println("Guest deleted successfully!");
            } else {
                System.out.println("Failed to delete guest!");
            }
        } else {
            System.out.println("Delete cancelled!");
        }
    }

    // Display guest details in a formatted way
    private void displayGuestDetails(GuestDto guest) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("\t\t GUEST DETAILS ");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Guest ID:       " + guest.getGuestId());
        System.out.println("Name:           " + guest.getName());
        System.out.println("Address:        " + guest.getAddress());
        System.out.println("Country:        " + guest.getCountry());
        System.out.println("NIC:            " + guest.getNic());
        System.out.println("Email:          " + guest.getEmail());
        System.out.println("Contact:        " + guest.getContact());
        System.out.println("Member Count:   " + guest.getMemberCount());
        System.out.println("-------------------------------------------------------------");
    }

    // Display guests
    private void displayGuestTable(List<GuestDto> guests) {
        System.out.println("\n----------------------------------------------------------------------------------------");
        System.out.printf("%-8s %-15s %-15s %-10s %-20s %-12s %-6s\n",
                "ID", "Name", "Country", "NIC", "Email", "Contact", "Members");
        System.out.println("----------------------------------------------------------------------------------------");

        for (GuestDto guest : guests) {
            // NIC විතරක් truncate කරන්න
            String nic = guest.getNic().length() > 10 ? guest.getNic().substring(0, 7) + "..." : guest.getNic();

            System.out.printf("%-8s %-15s %-15s %-10s %-20s %-12s %-6d\n",
                    guest.getGuestId(),
                    guest.getName(),
                    guest.getCountry(),
                    nic,
                    guest.getEmail(),
                    guest.getContact(),
                    guest.getMemberCount()
            );
        }
        System.out.println("----------------------------------------------------------------------------------------");
    }
}