package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.Role;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeliveryAgentDAO {
    public int addDeliveryAgent(DeliveryAgent agent) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int userId = UserDAO.insertUser(conn, agent); // shared

            String query = "INSERT INTO delivery_agents(delivery_agent_id) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            conn.commit();
            return userId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add delivery agent", e);
        }
    }

    public List<DeliveryAgent> getAllDeliveryAgent() {
        List<User> users = UserDAO.getAllUsers(Role.DELIVERY_AGENT);
        return users.stream().map(u->(DeliveryAgent)u).toList();
    }

    public void removeAgent(int id) {
        UserDAO.deleteById(id);
    }
}
