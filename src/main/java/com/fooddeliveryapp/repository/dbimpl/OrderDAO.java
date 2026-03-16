package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.enums.OrderStatus;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.order.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public List<OrderItem> getOrderItems(int orderId)
    {
        String query = """
                    SELECT order_item_id, menu_item_id, quantity, price_at_order_item
                    FROM order_item
                    WHERE order_id = ?
                    """;

        List<OrderItem> items = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                OrderItem item = new OrderItem(
                        rs.getInt("menu_item_id"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("price_at_order_item")
                );
                item.setId(rs.getInt("order_item_id"));
                item.setOrderId(orderId);
                items.add(item);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't get order items" + e);
        }
        return items;
    }
    public int placeOrder(Order order)
    {
        String orderQuery = """
                            INSERT INTO orders (customer_id, address_id, discount_rate, total_amount)
                            VALUES (?, ?, ?, ?)
                            RETURNING order_id
                            """;

        String orderItemQuery = """
                                INSERT INTO order_item (order_id, menu_item_id, quantity, price_at_order_item)
                                VALUES (?, ?, ?, ?)
                                """;

        try (Connection conn = DBConnection.getConnection())
        {
            conn.setAutoCommit(false);

            int orderId;
            try (PreparedStatement stmt = conn.prepareStatement(orderQuery))
            {
                stmt.setInt(1, order.getCustomerId());
                stmt.setInt(2, order.getAddressId());
                stmt.setDouble(3, order.getDiscountRate());
                stmt.setBigDecimal(4, order.getTotalAmount());

                ResultSet rs = stmt.executeQuery();
                rs.next();
                orderId = rs.getInt("order_id");
            }

            try (PreparedStatement stmt = conn.prepareStatement(orderItemQuery))
            {
                for (OrderItem item : order.getOrderItems())
                {
                    stmt.setInt(1, orderId);
                    stmt.setInt(2, item.getMenuItemId());
                    stmt.setInt(3, item.getQuantity());
                    stmt.setBigDecimal(4, item.getPriceAtOrder());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
            return orderId;
        }
        catch (SQLException e)
        {
            System.out.println("Order not placed" + e);
            return -1;
        }
    }

    public List<Order> getOrdersByCustomerId(int customerId)
    {
        String query = """
                        SELECT order_id, customer_id, delivery_agent_id,
                               total_amount, discount_rate, status,
                               address_id, created_at
                        FROM orders
                        WHERE customer_id = ?
                        ORDER BY created_at DESC
                        """;

        List<Order> orders = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                Order order = mapRow(rs);
                orders.add(order);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't get orders" + e);
        }
        return orders;
    }

    public List<Order> getAllOrders()
    {
        String query = """
                        SELECT order_id, customer_id, delivery_agent_id,
                               total_amount, discount_rate, status,
                               address_id, created_at
                        FROM orders
                        ORDER BY created_at DESC
                        """;

        List<Order> orders = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                orders.add(mapRow(rs));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't get orders" + e);
        }
        return orders;
    }

    public List<Order> getPendingOrders()
    {
        String query = """
                        SELECT order_id, customer_id, delivery_agent_id,
                               total_amount, discount_rate, status,
                               address_id, created_at
                        FROM orders
                        WHERE status = 'PENDING'
                        ORDER BY created_at ASC
                        """;

        List<Order> orders = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                orders.add(mapRow(rs));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't get pending orders" + e);
        }
        return orders;
    }

    public void updateOrderStatus(int orderId, OrderStatus status)
    {
        String query = """
                        UPDATE orders
                        SET status = ?::order_status
                        WHERE order_id = ?
                        """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, status.name());
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Status not updated" + e);
        }
    }

    public void assignDeliveryAgent(int orderId, int deliveryAgentId)
    {
        String query = """
                        UPDATE orders
                        SET delivery_agent_id = ?, status = 'CONFIRMED'::order_status
                        WHERE order_id = ?
                        """;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, deliveryAgentId);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Agent not assigned" + e);
        }
    }

    public List<Order> getOrdersByDeliveryAgentId(int deliveryAgentId)
    {
        String query = """
                        SELECT order_id, customer_id, delivery_agent_id,
                               total_amount, discount_rate, status,
                               address_id, created_at
                        FROM orders
                        WHERE delivery_agent_id = ?
                        ORDER BY created_at DESC
                        """;

        List<Order> orders = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, deliveryAgentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                orders.add(mapRow(rs));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't get orders" + e);
        }
        return orders;
    }

    private Order mapRow(ResultSet rs) throws SQLException
    {
        Order order = new Order(
                rs.getInt("customer_id"),
                rs.getInt("delivery_agent_id"),
                rs.getBigDecimal("total_amount"),
                rs.getInt("address_id"),
                OrderStatus.valueOf(rs.getString("status")),
                new ArrayList<>()
        );
        order.setId(rs.getInt("order_id"));
        order.setDiscountRate(rs.getDouble("discount_rate"));
        return order;
    }
}