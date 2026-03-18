package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.Role;
import com.fooddeliveryapp.model.user.Admin;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {


    private static User mapRow(ResultSet rs, Role role) throws SQLException {
        User user = switch (role) {
            case CUSTOMER -> new Customer();
            case ADMIN -> new Admin();
            case DELIVERY_AGENT -> new DeliveryAgent();
        };
        user.setRole(role);
        user.setId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setActive(rs.getBoolean("is_active"));
        return user;
    }

    private static User mapRowWithPassword(ResultSet rs, Role role) throws SQLException {
        User user = mapRow(rs, role);
        user.setPassword(rs.getString("password"));
        return user;
    }

    public static int insertUser(Connection conn, User user) throws SQLException {
        String query = """
                INSERT INTO users(role, name, email, password, phone, is_active)
                VALUES (?::roles, ?, ?, ?, ?, true)
                RETURNING user_id
                """;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getRole().name());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPhone());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            } else {
                throw new RuntimeException("Failed to get user_id");
            }
        }
    }

    public static List<User> getAllUsers(Role role) {
        String query = """
                SELECT u.user_id, u.name, u.email, u.phone, u.role, u.is_active
                FROM users u
                WHERE u.role = ?::roles
                AND u.is_active = true
                """;
        List<User> users = new ArrayList<>();
        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, role.name());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(mapRow(resultSet, role));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get users", e);
        }
    }

    public static void deleteById(int id) {
        String query = """
                UPDATE users
                SET is_active = false
                WHERE user_id = ?
                """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove user", e);
        }
    }

    public static User findUserById(int id) {
        String query = """
                SELECT u.user_id, u.name, u.email, u.phone, u.role, u.is_active, u.password
                FROM users u
                WHERE u.user_id = ? AND u.is_active = true
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowWithPassword(rs, Role.valueOf(rs.getString("role")));
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by id", e);
        }
    }

    public static User findUserByEmail(String email) {
        String query = """
                SELECT u.user_id, u.name, u.email, u.phone, u.role, u.is_active, u.password
                FROM users u
                WHERE u.email = ? AND u.is_active = true
                """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowWithPassword(rs, Role.valueOf(rs.getString("role")));
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by email", e);
        }
    }
}