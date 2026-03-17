package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.model.discount.Discount;

import java.sql.*;

public class DiscountRepository {

    public int saveDiscount(Discount discount) {
        String deactivateQuery = """
                UPDATE discounts
                SET is_active = false
                WHERE is_active = true
                """;

        String insertQuery = """
                INSERT INTO discounts (name, min_amount, rate, is_active)
                VALUES (?, ?, ?, true)
                RETURNING discount_id
                """;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(deactivateQuery)) {
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, discount.getName());
                stmt.setBigDecimal(2, discount.getMinAmount());
                stmt.setInt(3, discount.getRate());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("discount_id");
                    discount.setId(id);
                    conn.commit();
                    return id;
                }
                throw new RuntimeException("Failed to get discount_id");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save discount", e);
        }
    }

    public Discount getActiveDiscount() {
        String query = """
                SELECT discount_id, name, min_amount, rate, is_active
                FROM discounts
                WHERE is_active = true
                LIMIT 1
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Discount discount = new Discount(
                        rs.getString("name"),
                        rs.getBigDecimal("min_amount"),
                        rs.getInt("rate")
                );
                discount.setId(rs.getInt("discount_id"));
                discount.setActive(rs.getBoolean("is_active"));
                return discount;
            }
            return new Discount();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get discount", e);
        }
    }
}