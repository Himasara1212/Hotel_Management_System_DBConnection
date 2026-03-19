package com.sanduni.Model;

import com.sanduni.Dto.GuestDto;
import com.sanduni.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestModel {

    public boolean saveGuest(GuestDto guestDto) throws SQLException {
        String sql = "INSERT INTO Guest (guest_id, name, address, country, nic, email, contact, member_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, guestDto.getGuestId());
        pstm.setString(2, guestDto.getName());
        pstm.setString(3, guestDto.getAddress());
        pstm.setString(4, guestDto.getCountry());
        pstm.setString(5, guestDto.getNic());
        pstm.setString(6, guestDto.getEmail());
        pstm.setString(7, guestDto.getContact());
        pstm.setInt(8, guestDto.getMemberCount());

        int rows = pstm.executeUpdate();

        return rows > 0;
    }

    public boolean updateGuest(GuestDto guestDto) throws SQLException {
        String sql = "UPDATE Guest SET name = ?, address = ?, country = ?, nic = ?, email = ?, contact = ?, member_count = ? WHERE guest_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, guestDto.getName());
        pstm.setString(2, guestDto.getAddress());
        pstm.setString(3, guestDto.getCountry());
        pstm.setString(4, guestDto.getNic());
        pstm.setString(5, guestDto.getEmail());
        pstm.setString(6, guestDto.getContact());
        pstm.setInt(7, guestDto.getMemberCount());
        pstm.setString(8, guestDto.getGuestId());

        int rows = pstm.executeUpdate();

        return rows > 0;
    }

    public boolean deleteGuest(String guestId) throws SQLException {
        String sql = "DELETE FROM Guest WHERE guest_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, guestId);

        int rows = pstm.executeUpdate();

        return rows > 0;
    }

    public GuestDto getGuest(String guestId) throws SQLException {
        String sql = "SELECT * FROM Guest WHERE guest_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, guestId);

        ResultSet rs = pstm.executeQuery();
        GuestDto guestDto = null;

        if (rs.next()) {
            guestDto = new GuestDto(
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

        return guestDto;
    }

    public List<GuestDto> getAllGuests() throws SQLException {
        String sql = "SELECT * FROM Guest ORDER BY guest_id";
        List<GuestDto> guests = new ArrayList<>();

        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        while (rs.next()) {
            GuestDto guestDto = new GuestDto(
                    rs.getString("guest_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("country"),
                    rs.getString("nic"),
                    rs.getString("email"),
                    rs.getString("contact"),
                    rs.getInt("member_count")
            );
            guests.add(guestDto);
        }

        return guests;
    }

    public List<GuestDto> searchGuests(String searchTerm) throws SQLException {
        String sql = "SELECT * FROM Guest WHERE name LIKE ? OR nic LIKE ? OR email LIKE ? ORDER BY guest_id";
        List<GuestDto> guests = new ArrayList<>();

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        String pattern = "%" + searchTerm + "%";
        pstm.setString(1, pattern);
        pstm.setString(2, pattern);
        pstm.setString(3, pattern);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            GuestDto guestDto = new GuestDto(
                    rs.getString("guest_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("country"),
                    rs.getString("nic"),
                    rs.getString("email"),
                    rs.getString("contact"),
                    rs.getInt("member_count")
            );
            guests.add(guestDto);
        }

        return guests;
    }

    public String getNextGuestId() throws SQLException {
        String sql = "SELECT guest_id FROM Guest ORDER BY guest_id DESC LIMIT 1";

        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        String nextId = "G001";

        if (rs.next()) {
            String lastId = rs.getString("guest_id");
            int number = Integer.parseInt(lastId.substring(1)) + 1;
            nextId = String.format("G%03d", number);
        }

        return nextId;
    }

    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Guest WHERE email = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, email);

        ResultSet rs = pstm.executeQuery();
        boolean exists = false;

        if (rs.next()) {
            exists = rs.getInt(1) > 0;
        }

        return exists;
    }

    public boolean isNicExists(String nic) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Guest WHERE nic = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, nic);

        ResultSet rs = pstm.executeQuery();
        boolean exists = false;

        if (rs.next()) {
            exists = rs.getInt(1) > 0;
        }

        return exists;
    }
}