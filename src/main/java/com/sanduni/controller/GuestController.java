package com.sanduni.controller;

import com.sanduni.Dto.GuestDto;
import com.sanduni.Model.GuestModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GuestController {

    private GuestModel guestModel = new GuestModel();
    private Scanner scanner  = new Scanner(System.in);


    public void guestMenu() {
        while (true) {
            System.out.println("===================================================");
            System.out.println("\t\t GUEST MANAGEMENT SYSTEM ");
            System.out.println("===================================================");
            System.out.println("1. Add New Guest");
            System.out.println("2. View All Guests");
            System.out.println("3. Search Guest");
            System.out.println("4. Update Guest");
            System.out.println("5. Delete Guest");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        addGuest();
                        break;
                    case 2:
                        viewAllGuests();
                        break;
                    case 3:
                        searchGuest();
                        break;
                    case 4:
                        updateGuest();
                        break;
                    case 5:
                        deleteGuest();
                        break;
                    case 9:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void addGuest() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t ADD NEW GUEST ");
        System.out.println("===================================================");

        String guestId = guestModel.getNextGuestId();
        System.out.println("Guest ID: " + guestId);

        System.out.println("Enter GUEST NAME: ");
        String name = scanner.next();

        System.out.println("Address: ");
        String address = scanner.next();

        System.out.println("Country: ");
        String country = scanner.next();

        System.out.println("NIC: ");
        String nic = scanner.next();
        if (guestModel.isNicExists(nic)) {
            System.out.println("NIC already exists");
            return;
        }

        System.out.println("Email: ");
        String email = scanner.next();
        if (guestModel.isEmailExists(email)) {
            System.out.println("Email already exists");
            return;
        }

        System.out.println("Phone number: ");
        String contact = scanner.next();

        System.out.println("memberCount: ");
        int memberCount = scanner.nextInt();

        GuestDto guestDto = new GuestDto(guestId, name, address, country, nic, email, contact, memberCount);

        if (guestModel.saveGuest(guestDto)) {
            System.out.println("Guest Added Successfully");
        } else {
            System.out.println("Guest Added Failed");
        }
    }

    private void viewAllGuests() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t VIEW ALL GUEST ");
        System.out.println("===================================================");

        List<GuestDto> guest = guestModel.getAllGuests();

        if (guest.isEmpty()) {
            System.out.println("No guests found");
            return;
        }

        // Format specifications
        String format = "| %-5s | %-15s | %-15s | %-10s | %-12s | %-20s | %-12s | %-5s |%n";

        // Print top border
        System.out.format("+-------+-----------------+-----------------+------------+--------------+----------------------+--------------+--------+%n");
        // Print header
        System.out.format(format, "ID", "Name", "Address", "Country", "NIC", "Email", "Contact", "Count");
        // Print separator
        System.out.format("+-------+-----------------+-----------------+------------+--------------+----------------------+--------------+--------+%n");

        // Print each row
        for (GuestDto guestDto : guest) {
            System.out.format(format,
                    guestDto.getGuestId(),
                    truncate(guestDto.getName(), 15),
                    truncate(guestDto.getAddress(), 15),
                    truncate(guestDto.getCountry(), 10),
                    truncate(guestDto.getNic(), 12),
                    truncate(guestDto.getEmail(), 20),
                    truncate(guestDto.getContact(), 12),
                    guestDto.getMemberCount()
            );
        }

        // Print bottom border
        System.out.format("+-------+-----------------+-----------------+------------+--------------+----------------------+--------------+--------+%n");
    }

    // Helper method to truncate long strings
    private String truncate(String value, int length) {
        if (value == null) return "";
        if (value.length() <= length) return value;
        return value.substring(0, length - 3) + "...";
    }

    private void searchGuest() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t SEARCH GUEST ");
        System.out.println("===================================================");

        System.out.print("Enter Search guestID: ");
        String searchId = scanner.next();

        GuestDto guest = guestModel.getGuest(searchId);

        if (guest != null) {
            System.out.println("===================================================");
            System.out.println("\t\t GUEST DETAILS ");
            System.out.println("===================================================");
            System.out.println("Guest ID: " + guest.getGuestId());
            System.out.println("Name: " + guest.getName());
            System.out.println("Address: " + guest.getAddress());
            System.out.println("Country: " + guest.getCountry());
            System.out.println("NIC: " + guest.getNic());
            System.out.println("Email: " + guest.getEmail());
            System.out.println("Phone: " + guest.getContact());
            System.out.println("Member Count: " + guest.getMemberCount());
            System.out.println("===================================================");
        } else {
            System.out.println("No guest ID: " + searchId);
        }
    }

    private void updateGuest() throws SQLException {
        System.out.println("===================================================");
        System.out.println("\t\t GUEST UPDATE ");
        System.out.println("===================================================");

        System.out.print("Enter Update guestID: ");
        String guest_id = scanner.next();
        scanner.nextLine();

        GuestDto guest = guestModel.getGuest(guest_id);

        if (guest == null) {
            System.out.println("No guest found: " + guest_id);
            return;
        }

        System.out.println("===================================================");
        System.out.println("\t\tCurrent Guest Details:");
        System.out.println("===================================================");
        System.out.println("1. Name: " + guest.getName());
        System.out.println("2. Address: " + guest.getAddress());
        System.out.println("3. Country: " + guest.getCountry());
        System.out.println("4. NIC: " + guest.getNic());
        System.out.println("5. Email: " + guest.getEmail());
        System.out.println("6. Contact: " + guest.getContact());
        System.out.println("7. Member Count: " + guest.getMemberCount());
        System.out.println("===================================================");

        System.out.println("\nEnter new details:");

        System.out.print("New Name: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) guest.setName(name);

        System.out.print("New Address: ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) guest.setAddress(address);

        System.out.print("New Country: ");
        String country = scanner.nextLine();
        if (!country.isEmpty()) guest.setCountry(country);

        System.out.print("New NIC: ");
        String nic = scanner.nextLine();
        if (!nic.isEmpty()) guest.setNic(nic);

        System.out.print("New Email: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) guest.setEmail(email);

        System.out.print("New Contact: ");
        String contact = scanner.nextLine();
        if (!contact.isEmpty()) guest.setContact(contact);

        System.out.print("New Member Count: ");
        String memberCountStr = scanner.nextLine();
        if (!memberCountStr.isEmpty()) {
            try {
                int memberCount = Integer.parseInt(memberCountStr);
                guest.setMemberCount(memberCount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid member count");
                return;
            }
        }

        if (guestModel.updateGuest(guest)) {
            System.out.println("Guest updated successfully");
        } else {
            System.out.println("Guest update failed");
        }
    }

}


