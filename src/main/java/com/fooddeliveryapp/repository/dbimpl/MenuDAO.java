package com.fooddeliveryapp.repository.dbimpl;

import com.fooddeliveryapp.dbconnection.DBConnection;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    public List<MenuItem> getAvailableMenuItems()
    {
        String query = """
                    SELECT menu_item_id, name, price, available, category_id
                    FROM menu_items
                    WHERE available = true
                    """;
        List<MenuItem> menuItems = new ArrayList<>();
        try(
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                MenuItem item = new MenuItem(rs.getBigDecimal("price"), rs.getString("name"));
                item.setId(rs.getInt("menu_item_id"));
                item.setAvailable(true);
                menuItems.add(item);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't get available menu items" + e);
        }
        return menuItems;
    }

    public List<MenuItem> getAllMenuItems()
    {
        String query = """
                    SELECT menu_item_id, name, price, available, category_id
                    FROM menu_items
                    """;
        List<MenuItem> menuItems = new ArrayList<>();
        try(
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                MenuItem item = new MenuItem(rs.getBigDecimal("price"), rs.getString("name"));
                item.setId(rs.getInt("menu_item_id"));
                item.setAvailable(rs.getBoolean("available"));
                menuItems.add(item);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't get menu items" + e);
        }
        return menuItems;
    }

    public List<MenuCategory> getAllMenuCategories()
    {
        String query = """
                    SELECT category_id, name
                    FROM menu_categories
                    """;
        List<MenuCategory> categories = new ArrayList<>();
        try(
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                MenuCategory category = new MenuCategory(rs.getString("name"));
                category.setId(rs.getInt("category_id"));
                categories.add(category);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't get menu categories" + e);
        }
        return categories;
    }
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
