package com.sanduni.Model;

import com.sanduni.Dto.BookingDetailsDto;
import com.sanduni.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDetails {

    public boolean saveBooking(BookingDetailsDto booking) throws SQLException {
        String sql = "INSERT INTO Booking_Details (booking_id, booking_date, booking_time, guest_id, check_in_date, check_out_date, price) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, booking.getBookingId());
            pstmt.setString(2, booking.getBookingDate());
            pstmt.setString(3, booking.getBookingTime());
            pstmt.setString(4, booking.getGuestId());
            pstmt.setString(5, booking.getCheckInDate());
            pstmt.setString(6, booking.getCheckOutDate());
            pstmt.setDouble(7, booking.getPrice());

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<BookingDetailsDto> getAllBookings() throws SQLException {
        List<BookingDetailsDto> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Booking_Details";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BookingDetailsDto booking = new BookingDetailsDto();
                booking.setBookingId(rs.getString("booking_id"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setBookingTime(rs.getString("booking_time"));
                booking.setGuestId(rs.getString("guest_id"));
                booking.setCheckInDate(rs.getString("check_in_date"));
                booking.setCheckOutDate(rs.getString("check_out_date"));
                booking.setPrice(rs.getDouble("price"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public BookingDetailsDto getBooking(String bookingId) throws SQLException {
        String sql = "SELECT * FROM Booking_Details WHERE booking_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                BookingDetailsDto booking = new BookingDetailsDto();
                booking.setBookingId(rs.getString("booking_id"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setBookingTime(rs.getString("booking_time"));
                booking.setGuestId(rs.getString("guest_id"));
                booking.setCheckInDate(rs.getString("check_in_date"));
                booking.setCheckOutDate(rs.getString("check_out_date"));
                booking.setPrice(rs.getDouble("price"));
                return booking;
            }
        }
        return null;
    }

    public List<BookingDetailsDto> getBookingsByGuestId(String guestId) throws SQLException {
        List<BookingDetailsDto> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Booking_Details WHERE guest_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, guestId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BookingDetailsDto booking = new BookingDetailsDto();
                booking.setBookingId(rs.getString("booking_id"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setBookingTime(rs.getString("booking_time"));
                booking.setGuestId(rs.getString("guest_id"));
                booking.setCheckInDate(rs.getString("check_in_date"));
                booking.setCheckOutDate(rs.getString("check_out_date"));
                booking.setPrice(rs.getDouble("price"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public boolean updateBooking(BookingDetailsDto booking) throws SQLException {
        String sql = "UPDATE Booking_Details SET booking_date = ?, booking_time = ?, guest_id = ?, check_in_date = ?, check_out_date = ?, price = ? WHERE booking_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, booking.getBookingDate());
            pstmt.setString(2, booking.getBookingTime());
            pstmt.setString(3, booking.getGuestId());
            pstmt.setString(4, booking.getCheckInDate());
            pstmt.setString(5, booking.getCheckOutDate());
            pstmt.setDouble(6, booking.getPrice());
            pstmt.setString(7, booking.getBookingId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteBooking(String bookingId) throws SQLException {
        String sql = "DELETE FROM Booking_Details WHERE booking_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookingId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public String getNextBookingId() throws SQLException {
        String sql = "SELECT booking_id FROM Booking_Details ORDER BY booking_id DESC LIMIT 1";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String lastId = rs.getString("booking_id");
                int num = Integer.parseInt(lastId.substring(1));
                num++;
                return String.format("B%03d", num);
            }
        }
        return "B001";
    }

    public boolean isBookingExists(String bookingId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Booking_Details WHERE booking_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public int getTotalBookingsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Booking_Details";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}