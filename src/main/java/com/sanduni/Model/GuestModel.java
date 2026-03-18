package com.sanduni.Model;

import com.sanduni.Dto.GuestDto;
import com.sanduni.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestModel {

    public boolean saveGuest(GuestDto guestDto) throws SQLException {
        String sql = "INSERT INTO Guest (guest_id, name, address, country, nic, email, contact, member_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, guestDto.getGuestId());
            pstm.setString(2, guestDto.getName());
            pstm.setString(3, guestDto.getAddress());
            pstm.setString(4, guestDto.getCountry());
            pstm.setString(5, guestDto.getNic());
            pstm.setString(6, guestDto.getEmail());
            pstm.setString(7, guestDto.getContact());
            pstm.setInt(8, guestDto.getMemberCount());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean updateGuest(GuestDto guestDto) throws SQLException {
        String sql = "UPDATE Guest SET name = ?, address = ?, country = ?, nic = ?, email = ?, contact = ?, member_count = ? WHERE guest_id = ?";

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, guestDto.getName());
            pstm.setString(2, guestDto.getAddress());
            pstm.setString(3, guestDto.getCountry());
            pstm.setString(4, guestDto.getNic());
            pstm.setString(5, guestDto.getEmail());
            pstm.setString(6, guestDto.getContact());
            pstm.setInt(7, guestDto.getMemberCount());
            pstm.setString(8, guestDto.getGuestId());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean deleteGuest(String guestId) throws SQLException {
        String sql = "DELETE FROM Guest WHERE guest_id = ?";

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, guestId);
            return pstm.executeUpdate() > 0;
        }
    }

    public GuestDto getGuest(String guestId) throws SQLException {
        String sql = "SELECT * FROM Guest WHERE guest_id = ?";

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, guestId);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return extractGuestFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public GuestDto getGuestByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Guest WHERE email = ?";

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, email);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return extractGuestFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public GuestDto getGuestByNic(String nic) throws SQLException {
        String sql = "SELECT * FROM Guest WHERE nic = ?";

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, nic);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return extractGuestFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<GuestDto> getAllGuests() throws SQLException {
        String sql = "SELECT * FROM Guest ORDER BY guest_id";
        List<GuestDto> guests = new ArrayList<>();

        try (Statement stm = DBConnection.getInstance().getConnection().createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                guests.add(extractGuestFromResultSet(rs));
            }
        }
        return guests;
    }

    public List<GuestDto> searchGuests(String searchTerm) throws SQLException {
        String sql = "SELECT * FROM Guest WHERE guest_id LIKE ? OR name LIKE ? OR nic LIKE ? OR email LIKE ? OR contact LIKE ? ORDER BY guest_id";
        List<GuestDto> guests = new ArrayList<>();

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            String pattern = "%" + searchTerm + "%";
            pstm.setString(1, pattern);
            pstm.setString(2, pattern);
            pstm.setString(3, pattern);
            pstm.setString(4, pattern);
            pstm.setString(5, pattern);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    guests.add(extractGuestFromResultSet(rs));
                }
            }
        }
        return guests;
    }

    public String getNextGuestId() throws SQLException {
        String sql = "SELECT guest_id FROM Guest ORDER BY guest_id DESC LIMIT 1";

        try (Statement stm = DBConnection.getInstance().getConnection().createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            if (rs.next()) {
                String lastId = rs.getString("guest_id");
                int number = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("G%03d", number);
            }
        }
        return "G001";
    }

    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Guest WHERE email = ?";

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, email);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean isNicExists(String nic) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Guest WHERE nic = ?";

        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, nic);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private GuestDto extractGuestFromResultSet(ResultSet rs) throws SQLException {
        return new GuestDto(
                rs.getString("guest_id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("country"),
                rs.getString("nic"),
                rs.getString("email"),
                rs.getString("contact"),
                rs.getInt("member_count")
        );
    }
}