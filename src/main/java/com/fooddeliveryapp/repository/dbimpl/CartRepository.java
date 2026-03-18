package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.model.cart.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    public List<CartItem> getAllCartItem(int customerId) {
        String cartQuery = """
                SELECT ci.cart_item_id,ci.menu_item_id,ci.quantity,ci.price
                FROM cart_item ci
                JOIN cart c USING(cart_id)
                WHERE c.customer_id = ?
                """;

        List<CartItem> cartItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(cartQuery)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("cart_item_id"));
                cartItem.setMenuItemId(rs.getInt("menu_item_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setPrice(rs.getBigDecimal("price"));

                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            System.out.println("Can't get items" + e);
        }
        return cartItems;
    }

    public void createCart(Connection conn, int customerId) throws SQLException {
        String cartQuery = """
                INSERT INTO cart (customer_id)
                VALUES (?)
                """;
        try (PreparedStatement stmt = conn.prepareStatement(cartQuery)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        }
    }

    public void addItemToCart(CartItem cartItem) {
        String query = """
                INSERT INTO cart_item (cart_id,menu_item_id,quantity,price)
                VALUES(?,?,?,?)
                """;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cartItem.getCartId());
            stmt.setInt(2, cartItem.getMenuItemId());
            stmt.setInt(3, cartItem.getQuantity());
            stmt.setBigDecimal(4, cartItem.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add item to cart", e);
        }
    }

    public void removeItemFromCart(int itemId) {
        String query = """
                DELETE FROM cart_item
                WHERE cart_item_id = ?
                """;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Item not removed" + e);
        }
    }

    public void updateQuantity(int quantity, int itemId) {
        String query = """
                UPDATE cart_item
                SET quantity = ?
                WHERE cart_item_id = ?
                """;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, itemId);
            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Quantity not updated" + e);
        }
    }

    public int getCartIdByCustomerId(int customerId) {
        String query = """
                SELECT cart_id FROM cart
                WHERE customer_id = ?
                """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("cart_id");
            }
            throw new RuntimeException("Cart not found for customer: " + customerId);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get cart id", e);
        }
    }

    public void clearCart(int customerId) {
        String query = """
                DELETE FROM cart_item
                WHERE cart_id = (
                    SELECT cart_id FROM cart WHERE customer_id = ?
                )
                """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear cart", e);
        }
    }
}
