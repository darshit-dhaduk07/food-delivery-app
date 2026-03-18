package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.Role;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryAgentRepository {
    public int addDeliveryAgent(DeliveryAgent agent) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int userId = UserRepository.insertUser(conn, agent); // shared

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
        String query = """
                SELECT u.user_id, u.name, u.email, u.phone, u.role, u.is_active,
                       da.availability, da.last_free_time, da.order_assign_time
                FROM delivery_agents da
                JOIN users u ON u.user_id = da.delivery_agent_id
                WHERE u.is_active = true
                ORDER BY u.user_id ASC
                """;

        List<DeliveryAgent> agents = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DeliveryAgent agent = new DeliveryAgent();
                agent.setId(rs.getInt("user_id"));
                agent.setName(rs.getString("name"));
                agent.setEmail(rs.getString("email"));
                agent.setPhone(rs.getString("phone"));
                agent.setAvailable(rs.getBoolean("availability"));
                agent.setOrderAssignTime(rs.getTimestamp("order_assign_time"));
                agent.setLastFreeTime(rs.getTimestamp("last_free_time"));
                agents.add(agent);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get delivery agents", e);
        }
        return agents;
    }

    public void removeAgent(int id) {
        UserRepository.deleteById(id);
    }

    public DeliveryAgent getAvailableAgent() {
        String query = """
                SELECT u.user_id, u.name, u.email, u.phone, u.role, u.is_active,
                       da.availability, da.last_free_time, da.order_assign_time
                FROM delivery_agents da
                JOIN users u ON u.user_id = da.delivery_agent_id
                WHERE da.availability = true AND u.is_active = true
                ORDER BY da.last_free_time ASC
                LIMIT 1
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DeliveryAgent agent = new DeliveryAgent();
                agent.setId(rs.getInt("user_id"));
                agent.setName(rs.getString("name"));
                agent.setEmail(rs.getString("email"));
                agent.setPhone(rs.getString("phone"));
                agent.setAvailable(rs.getBoolean("availability"));
                agent.setOrderAssignTime(rs.getTimestamp("order_assign_time"));
                agent.setLastFreeTime(rs.getTimestamp("last_free_time"));
                return agent;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get available agent", e);
        }
    }

    public void setAvailability(int agentId, boolean availability) {
        String query;
        if (availability) {
            query = """
                    UPDATE delivery_agents
                    SET availability = true, last_free_time = CURRENT_TIMESTAMP
                    WHERE delivery_agent_id = ?
                    """;
        } else {
            query = """
                    UPDATE delivery_agents
                    SET availability = false, order_assign_time = CURRENT_TIMESTAMP
                    WHERE delivery_agent_id = ?
                    """;
        }

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, agentId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update agent availability", e);
        }
    }
}
