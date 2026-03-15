package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MenuDAO {
    public void addMenuCategory(MenuCategory cat) {
        String query = """
                INSERT INTO menu_categories(name)
                VALUES(?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
        ) {
            stmt.setString(1, cat.getName());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add customer", e);
        }
    }

    public void addMenuItem(MenuItem item, int categoryId) {
        String query = """
                INSERT INTO menu_items(name,price,available,category_id)
                VALUES(?,?,?,?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
        ) {
            stmt.setString(1, item.getName());
            stmt.setBigDecimal(2, item.getPrice());
            stmt.setBoolean(3, item.isAvailable());
            stmt.setInt(4, categoryId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add menu item", e);
        }
    }
}
