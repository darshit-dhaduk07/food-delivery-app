package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.PaymentStatus;
import com.fooddeliveryapp.model.payment.Payment;

import java.sql.*;

public class PaymentRepository {

    public int savePayment(Payment payment) {
        String query = """
                INSERT INTO payments (order_id, payment_method, status, amount)
                VALUES (?, ?::payment_methods, ?::payment_status, ?)
                RETURNING payment_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, payment.getOrderId());
            stmt.setString(2, payment.getPaymentMethod().name());
            stmt.setString(3, payment.getStatus().name());
            stmt.setBigDecimal(4, payment.getAmount());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("payment_id");
                payment.setId(id);
                return id;
            }
            throw new RuntimeException("Failed to get payment_id");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save payment", e);
        }
    }

    public void updatePaymentStatus(int paymentId, PaymentStatus status) {
        String query = """
                UPDATE payments
                SET status = ?::payment_status
                WHERE payment_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, paymentId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update payment status", e);
        }
    }

    public Payment getPaymentByOrderId(int orderId) {
        String query = """
                SELECT payment_id, order_id, payment_method, status, amount, created_at
                FROM payments
                WHERE order_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("order_id"),
                        com.fooddeliveryapp.enums.PaymentMethod.valueOf(rs.getString("payment_method")),
                        rs.getBigDecimal("amount")
                );
                payment.setId(rs.getInt("payment_id"));
                payment.setStatus(PaymentStatus.valueOf(rs.getString("status")));
                payment.setCreatedAt(rs.getTimestamp("created_at"));
                return payment;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get payment", e);
        }
    }
}