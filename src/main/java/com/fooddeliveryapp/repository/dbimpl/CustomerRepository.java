package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.Role;
import com.fooddeliveryapp.model.user.Address;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final CartRepository cartRepository = new CartRepository();

    public List<Address> getAddressesByCustomerId(int customerId) {
        String query = """
                SELECT address_id, address, customer_id
                FROM addresses
                WHERE customer_id = ?
                """;

        List<Address> addresses = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Address address = new Address(rs.getString("address"), rs.getInt("customer_id"));
                address.setId(rs.getInt("address_id"));
                addresses.add(address);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get addresses", e);
        }
        return addresses;
    }

    public int addCustomer(Customer customer) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int userId = UserRepository.insertUser(conn, customer); // shared

            String customerQuery = "INSERT INTO customers(customer_id) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(customerQuery)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

//            create cart
            try {
                cartRepository.createCart(conn, userId);
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Failed to create cart", e);
            }

            conn.commit();
            return userId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add customer", e);
        }
    }


    public List<Customer> getAllCustomer() {
        List<User> users = UserRepository.getAllUsers(Role.CUSTOMER);
        return users.stream().map(u -> (Customer) u).toList();
    }

    public void removeCustomer(int id) {
        UserRepository.deleteById(id);
    }

    public int addAddress(Address address) {
        String query = """
                INSERT INTO addresses (address, customer_id)
                VALUES (?, ?)
                RETURNING address_id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, address.getAddressName());
            ps.setInt(2, address.getCustomerId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("address_id");
            }
            throw new RuntimeException("Failed to get address_id");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add address", e);
        }
    }
}
