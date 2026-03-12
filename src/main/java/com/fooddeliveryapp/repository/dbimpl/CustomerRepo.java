package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.Role;
import com.fooddeliveryapp.model.user.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepo {

    public int addCustomer(Customer customer) {

        String query =
                "INSERT INTO users(role,name,email,password,phone) VALUES (?,?,?,?,?) RETURNING user_id";

        String customerQuery =
                "INSERT INTO customers(user_id) VALUES (?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                PreparedStatement stmt2 = conn.prepareStatement(customerQuery)
        ) {

            conn.setAutoCommit(false);

            stmt.setString(1, customer.getRole().name());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getPhone());

            ResultSet rs = stmt.executeQuery();

            int userId;

            if (rs.next()) {
                userId = rs.getInt("user_id");
            } else {
                throw new RuntimeException("Failed to get user id");
            }

            stmt2.setInt(1, userId);
            stmt2.executeUpdate();

            conn.commit();

            return userId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert customer", e);
        }
    }

    public void getAllCustomer() {

    }
}
