package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.Role;
import com.fooddeliveryapp.model.user.Address;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerRepository {
    private CartRepository cartRepository;
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
            cartRepository.createCart(customer.getId());

            conn.commit();
            return userId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add customer", e);
        }
    }


    public List<Customer> getAllCustomer() {
        List<User> users = UserRepository.getAllUsers(Role.CUSTOMER);
        return users.stream().map(u->(Customer)u).toList();
    }

    public void removeCustomer(int id) {
        UserRepository.deleteById(id);
    }

    public void addAddress(Address address)
    {
        String query = """
                        INSERT INTO addresses (address,customer_id)
                        VALUES (?,?)
                        """;

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);){

            ps.setString(1,address.getAddressName());
            ps.setInt(2,address.getCustomerId());

            ps.executeUpdate();

        }catch (SQLException e)
        {
            throw new RuntimeException("Failed to add address", e);
        }
    }
}
