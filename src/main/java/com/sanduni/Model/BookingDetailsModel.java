package com.sanduni.Model;

import com.sanduni.Dto.BookingDetailsDto;
import com.sanduni.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDetailsModel {

    // Save
    public boolean saveBooking(BookingDetailsDto bookingDetailsDto) {
        String query = "INSERT INTO Booking_Details (booking_id, booking_date, booking_time, guest_id, check_in_date, check_out_date, price) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, bookingDetailsDto.getBookingId());
            pstmt.setString(2, bookingDetailsDto.getBookingDate());
            pstmt.setString(3, bookingDetailsDto.getBookingTime());
            pstmt.setString(4, bookingDetailsDto.getGuestId());
            pstmt.setString(5, bookingDetailsDto.getCheckInDate());
            pstmt.setString(6, bookingDetailsDto.getCheckOutDate());
            pstmt.setDouble(7, bookingDetailsDto.getPrice());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error save booking: " + e.getMessage());
            return false;
        }
    }

    // Get all bookings
    public List<BookingDetailsDto> getAllBookings() {
        List<BookingDetailsDto> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking_Details";

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                bookings.add(new BookingDetailsDto(
                        rs.getString("booking_id"),
                        rs.getString("booking_date"),
                        rs.getString("booking_time"),
                        rs.getString("guest_id"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieve bookings: " + e.getMessage());
        }

        return bookings;
    }

    // Get booking by ID
    public BookingDetailsDto getBookingById(String bookingId) {
        String query = "SELECT * FROM Booking_Details WHERE booking_id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new BookingDetailsDto(
                        rs.getString("booking_id"),
                        rs.getString("booking_date"),
                        rs.getString("booking_time"),
                        rs.getString("guest_id"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date"),
                        rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieve booking: " + e.getMessage());
        }

        return null;
    }

    // Update booking
    public boolean updateBooking(BookingDetailsDto bookingDetailsDto) {
        String query = "UPDATE Booking_Details SET booking_date=?, booking_time=?, guest_id=?, check_in_date=?, check_out_date=?, price=? WHERE booking_id=?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, bookingDetailsDto.getBookingDate());
            pstmt.setString(2, bookingDetailsDto.getBookingTime());
            pstmt.setString(3, bookingDetailsDto.getGuestId());
            pstmt.setString(4, bookingDetailsDto.getCheckInDate());
            pstmt.setString(5, bookingDetailsDto.getCheckOutDate());
            pstmt.setDouble(6, bookingDetailsDto.getPrice());
            pstmt.setString(7, bookingDetailsDto.getBookingId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error update booking: " + e.getMessage());
            return false;
        }
    }

    // Delete booking
    public boolean deleteBooking(String bookingId) {
        String query = "DELETE FROM Booking_Details WHERE booking_id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, bookingId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error delete booking: " + e.getMessage());
            return false;
        }
    }

    // Get bookings by guest ID
    public List<BookingDetailsDto> getBookingsByGuestId(String guestId) {
        List<BookingDetailsDto> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking_Details WHERE guest_id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, guestId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bookings.add(new BookingDetailsDto(
                        rs.getString("booking_id"),
                        rs.getString("booking_date"),
                        rs.getString("booking_time"),
                        rs.getString("guest_id"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error guest bookings: " + e.getMessage());
        }

        return bookings;
    }

    // Check if booking exists
    public boolean isBookingExists(String bookingId) {
        String query = "SELECT COUNT(*) FROM Booking_Details WHERE booking_id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error check booking: " + e.getMessage());
        }

        return false;
    }
}