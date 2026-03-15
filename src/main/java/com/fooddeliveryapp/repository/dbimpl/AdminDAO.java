package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.Role;
import com.fooddeliveryapp.model.user.Admin;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AdminDAO {

    public int addAdmin(Admin admin) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int userId = UserDAO.insertUser(conn, admin); // shared

            String query = "INSERT INTO admins(admin_id) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            conn.commit();
            return userId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add admin", e);
        }
    }


//    public List<Admin> getAllAdmins() {
//        List<User> users = UserDAO.getAllUsers(Role.CUSTOMER);
//        return users.stream().map(u->(Admin)u).toList();
//    }
//
//    public void removeAdmin(int id) {
//        UserDAO.deleteById(id);
//    }
}
