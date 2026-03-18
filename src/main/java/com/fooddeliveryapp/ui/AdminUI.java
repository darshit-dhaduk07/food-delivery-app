package com.fooddeliveryapp.ui;

import com.fooddeliveryapp.controller.AdminController;
import com.fooddeliveryapp.controller.AuthenticationController;
import com.fooddeliveryapp.model.discount.Discount;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Admin;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.model.user.User;
import com.fooddeliveryapp.utility.InputTaker;

import java.math.BigDecimal;
import java.util.List;

public class AdminUI {

    private final AuthenticationController authController;
    private final AdminController adminController;
    private Admin loggedInAdmin;

    public AdminUI(AuthenticationController authController,
                   AdminController adminController) {
        this.authController = authController;
        this.adminController = adminController;
    }

    // Login

    public void menu() {
        System.out.println("\n┌─── Admin Login ────────────────────");
        String email    = InputTaker.readString("│  Email    : ");
        String password = InputTaker.readString("│  Password : ");
        System.out.println("└────────────────────────────────────");
        try {
            User user = authController.login(email, password);
            if (user instanceof Admin admin) {
                loggedInAdmin = admin;
                System.out.println("\n✅ Welcome, Admin " + admin.getName());
                adminPanel();
            } else {
                System.out.println("❌ This account is not an admin account.");
            }
        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }

    // Panel — split into sub-menus

    private void adminPanel() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║        ADMIN PANEL           ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Menu Management          ║");
            System.out.println("║  2. Agent Management         ║");
            System.out.println("║  3. Reports                  ║");
            System.out.println("║  4. Discount                 ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  0. Logout                   ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputTaker.readInt("Select: ");
            switch (choice) {
                case 1 -> menuManagement();
                case 2 -> agentManagement();
                case 3 -> reports();
                case 4 -> discountManagement();
                case 0 -> {
                    loggedInAdmin = null;
                    System.out.println("\n👋 Logged out.");
                    return;
                }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    //  Menu Management Sub-Panel

    private void menuManagement() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║      MENU MANAGEMENT         ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. View Menu Items          ║");
            System.out.println("║  2. Add Menu Item            ║");
            System.out.println("║  3. Remove Menu Item         ║");
            System.out.println("║  4. View Categories          ║");
            System.out.println("║  5. Add Category             ║");
            System.out.println("║  6. Remove Category          ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  0. Back                     ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputTaker.readInt("Select: ");
            switch (choice) {
                case 1 -> viewAllMenuItems();
                case 2 -> addMenuItem();
                case 3 -> removeMenuItem();
                case 4 -> viewAllCategories();
                case 5 -> addMenuCategory();
                case 6 -> removeMenuCategory();
                case 0 -> { return; }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    //  Agent Management Sub-Panel

    private void agentManagement() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║     AGENT MANAGEMENT         ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. View All Agents          ║");
            System.out.println("║  2. Add Delivery Agent       ║");
            System.out.println("║  3. Remove Delivery Agent    ║");
            System.out.println("║  4. Assign Agent to Order    ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  0. Back                     ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputTaker.readInt("Select: ");
            switch (choice) {
                case 1 -> viewAllDeliveryAgents();
                case 2 -> addDeliveryAgent();
                case 3 -> removeDeliveryAgent();
                case 4 -> manualAssignAgent();
                case 0 -> { return; }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    // Reports Sub-Panel

    private void reports() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║          REPORTS             ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. View All Orders          ║");
            System.out.println("║  2. View All Customers       ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  0. Back                     ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputTaker.readInt("Select: ");
            switch (choice) {
                case 1 -> viewAllOrders();
                case 2 -> viewAllCustomers();
                case 0 -> { return; }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    // Discount Sub-Panel

    private void discountManagement() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║        DISCOUNT              ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Set Discount             ║");
            System.out.println("║  2. View Active Discount     ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  0. Back                     ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputTaker.readInt("Select: ");
            switch (choice) {
                case 1 -> setDiscount();
                case 2 -> viewActiveDiscount();
                case 0 -> { return; }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    // Menu Methods

    private void viewAllMenuItems() {
        List<MenuCategory> categories = adminController.getAllMenuCategories();
        List<MenuItem> items = adminController.getAllMenuItems();
        System.out.println("\n┌─── Menu Items by Category ─────────────────────────────────────");
        if (categories.isEmpty()) {
            System.out.println("│  No categories found.");
        } else {
            for (MenuCategory cat : categories) {
                System.out.printf("│%n│  ── %s%n", cat.getName());
                System.out.printf("│  %-5s  %-25s  %-10s  %-10s%n",
                        "ID", "Name", "Price", "Available");
                System.out.println("│  " + "─".repeat(55));
                List<MenuItem> catItems = items.stream()
                        .filter(i -> i.getCategoryId() == cat.getId())
                        .toList();
                if (catItems.isEmpty()) {
                    System.out.println("│  No items in this category.");
                } else {
                    for (MenuItem item : catItems) {
                        System.out.printf("│  %-5d  %-25s  ₹%-9s  %s%n",
                                item.getId(), item.getName(),
                                item.getPrice(),
                                item.isAvailable() ? "Yes" : "No");
                    }
                }
            }
        }
        System.out.println("│");
        System.out.println("└────────────────────────────────────────────────────────────────");
    }

    private void viewAllCategories() {
        List<MenuCategory> categories = adminController.getAllMenuCategories();
        System.out.println("\n┌─── Menu Categories ────────────────");
        if (categories.isEmpty()) {
            System.out.println("│  No categories found.");
        } else {
            System.out.printf("│  %-5s  %-25s%n", "ID", "Name");
            System.out.println("│  " + "─".repeat(32));
            for (MenuCategory cat : categories) {
                System.out.printf("│  %-5d  %s%n", cat.getId(), cat.getName());
            }
        }
        System.out.println("└────────────────────────────────────");
    }

    private void addMenuItem() {
        List<MenuCategory> categories = adminController.getAllMenuCategories();
        if (categories.isEmpty()) {
            System.out.println("⚠ No categories. Add a category first.");
            return;
        }
        viewAllCategories();
        System.out.println("\n┌─── Add Menu Item ──────────────────");
        try {
            String name      = InputTaker.readString("│  Item Name   : ");
            BigDecimal price = InputTaker.readBigDecimal("│  Price (₹)   : ");
            List<Integer> validIds = categories.stream().map(MenuCategory::getId).toList();
            int categoryId   = InputTaker.readIdFromList("│  Category ID : ", validIds);
            System.out.println("└────────────────────────────────────");
            adminController.addMenuItem(name, price, categoryId);
            System.out.println("✅ Menu item added.");
        } catch (Exception e) {
            System.out.println("└────────────────────────────────────");
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void removeMenuItem() {
        List<MenuItem> items = adminController.getAllMenuItems();
        if (items.isEmpty()) {
            System.out.println("⚠ No menu items found.");
            return;
        }
        viewAllMenuItems();
        try {
            List<Integer> validIds = items.stream().map(MenuItem::getId).toList();
            int id = InputTaker.readIdFromList("Enter Menu Item ID to remove: ", validIds);
            adminController.removeMenuItem(id);
            System.out.println("✅ Menu item removed.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void addMenuCategory() {
        System.out.println("\n┌─── Add Category ───────────────────");
        try {
            String name = InputTaker.readString("│  Category Name: ");
            System.out.println("└────────────────────────────────────");
            adminController.addMenuCategory(name);
            System.out.println("✅ Category added.");
        } catch (Exception e) {
            System.out.println("└────────────────────────────────────");
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void removeMenuCategory() {
        List<MenuCategory> categories = adminController.getAllMenuCategories();
        if (categories.isEmpty()) {
            System.out.println("⚠ No categories found.");
            return;
        }
        viewAllCategories();
        try {
            List<Integer> validIds = categories.stream().map(MenuCategory::getId).toList();
            int id = InputTaker.readIdFromList("Enter Category ID to remove: ", validIds);
            adminController.removeMenuCategory(id);
            System.out.println("✅ Category removed.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // Agent Methods

    private void viewAllDeliveryAgents() {
        List<DeliveryAgent> agents = adminController.getAllDeliveryAgents();
        System.out.println("\n┌─── All Delivery Agents ──────────────────────────────────────────────");
        if (agents.isEmpty()) {
            System.out.println("│  No delivery agents found.");
        } else {
            System.out.printf("│  %-5s  %-15s  %-25s  %-13s  %-10s%n",
                    "ID", "Name", "Email", "Phone", "Status");
            System.out.println("│  " + "─".repeat(72));
            for (DeliveryAgent a : agents) {
                System.out.printf("│  %-5d  %-15s  %-25s  %-13s  %s%n",
                        a.getId(), a.getName(), a.getEmail(),
                        a.getPhone(),
                        a.isAvailable() ? "✅ Free" : "🚴 Busy");
            }
        }
        System.out.println("└──────────────────────────────────────────────────────────────────────");
    }

    private void addDeliveryAgent() {
        System.out.println("\n┌─── Add Delivery Agent ─────────────");
        try {
            String name     = InputTaker.readString("│  Name     : ");
            String email    = InputTaker.readString("│  Email    : ");
            String phone    = InputTaker.readString("│  Phone    : ");
            String password = InputTaker.readString("│  Password : ");
            System.out.println("└────────────────────────────────────");
            adminController.addDeliveryAgent(name, email, phone, password);
            System.out.println("✅ Delivery agent added.");
        } catch (Exception e) {
            System.out.println("└────────────────────────────────────");
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void removeDeliveryAgent() {
        List<DeliveryAgent> agents = adminController.getAllDeliveryAgents();
        if (agents.isEmpty()) {
            System.out.println("⚠ No delivery agents found.");
            return;
        }
        viewAllDeliveryAgents();
        try {
            List<Integer> validIds = agents.stream().map(DeliveryAgent::getId).toList();
            int id = InputTaker.readIdFromList("Enter Agent ID to remove: ", validIds);
            adminController.removeDeliveryAgent(id);
            System.out.println("✅ Delivery agent removed.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void manualAssignAgent() {
        List<Order> unassigned = adminController.getUnassignedOrders();
        System.out.println("\n┌─── Unassigned Orders ──────────────────────────────");
        if (unassigned.isEmpty()) {
            System.out.println("│  No unassigned orders.");
            System.out.println("└────────────────────────────────────────────────────");
            return;
        }
        System.out.printf("│  %-8s  %-12s  %-12s%n", "Order ID", "Customer", "Amount");
        System.out.println("│  " + "─".repeat(36));
        for (Order o : unassigned) {
            System.out.printf("│  %-8d  %-12d  ₹%s%n",
                    o.getId(), o.getCustomerId(), o.getTotalAmount());
        }
        System.out.println("└────────────────────────────────────────────────────");

        List<DeliveryAgent> agents = adminController.getAvailableAgents();
        System.out.println("\n┌─── Available Agents ───────────────");
        if (agents.isEmpty()) {
            System.out.println("│  No agents available right now.");
            System.out.println("└────────────────────────────────────");
            return;
        }
        System.out.printf("│  %-5s  %-20s%n", "ID", "Name");
        System.out.println("│  " + "─".repeat(27));
        for (DeliveryAgent a : agents) {
            System.out.printf("│  %-5d  %s%n", a.getId(), a.getName());
        }
        System.out.println("└────────────────────────────────────");

        try {
            List<Integer> validOrderIds = unassigned.stream().map(Order::getId).toList();
            int orderId = InputTaker.readIdFromList("Select Order ID : ", validOrderIds);
            List<Integer> validAgentIds = agents.stream().map(DeliveryAgent::getId).toList();
            int agentId = InputTaker.readIdFromList("Select Agent ID : ", validAgentIds);
            adminController.manualAssignAgent(orderId, agentId);
            System.out.println("✅ Order #" + orderId + " assigned to agent #" + agentId);
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // Report Methods

    private void viewAllOrders() {
        List<Order> orders = adminController.getAllOrders();
        System.out.println("\n┌─── All Orders ──────────────────────────────────────────────────");
        if (orders.isEmpty()) {
            System.out.println("│  No orders found.");
        } else {
            System.out.printf("│  %-8s  %-10s  %-15s  %-12s  %-12s%n",
                    "Order ID", "Customer", "Status", "Total", "Agent");
            System.out.println("│  " + "─".repeat(62));
            for (Order o : orders) {
                System.out.printf("│  %-8d  %-10d  %-15s  ₹%-11s  %s%n",
                        o.getId(), o.getCustomerId(),
                        o.getOrderStatus(), o.getTotalAmount(),
                        o.getDeliveryAgentId() == 0 ? "Unassigned" : "#" + o.getDeliveryAgentId());
            }
        }
        System.out.println("└─────────────────────────────────────────────────────────────────");
    }

    private void viewAllCustomers() {
        List<Customer> customers = adminController.getAllCustomers();
        System.out.println("\n┌─── All Customers ─────────────────────────────────────────────────");
        if (customers.isEmpty()) {
            System.out.println("│  No customers found.");
        } else {
            System.out.printf("│  %-5s  %-15s  %-28s  %-13s%n",
                    "ID", "Name", "Email", "Phone");
            System.out.println("│  " + "─".repeat(65));
            for (Customer c : customers) {
                System.out.printf("│  %-5d  %-15s  %-28s  %s%n",
                        c.getId(), c.getName(), c.getEmail(), c.getPhone());
            }
        }
        System.out.println("└───────────────────────────────────────────────────────────────────");
    }

    //  Discount Methods

    private void setDiscount() {
        System.out.println("\n┌─── Set Discount ───────────────────");
        try {
            String name          = InputTaker.readString("│  Name           : ");
            BigDecimal minAmount = InputTaker.readBigDecimal("│  Min Amount (₹) : ");
            int rate             = InputTaker.readInt("│  Rate (%)       : ");
            System.out.println("└────────────────────────────────────");
            if (rate < 0 || rate > 100)
                throw new IllegalArgumentException("Rate must be between 0 and 100.");
            adminController.setDiscount(name, minAmount, rate);
            System.out.println("✅ Discount set successfully.");
        } catch (Exception e) {
            System.out.println("└────────────────────────────────────");
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void viewActiveDiscount() {
        Discount d = adminController.getActiveDiscount();
        System.out.println("\n┌─── Active Discount ────────────────");
        System.out.printf("│  Name       : %s%n", d.getName());
        System.out.printf("│  Min Amount : ₹%s%n", d.getMinAmount());
        System.out.printf("│  Rate       : %d%%%n", d.getRate());
        System.out.println("└────────────────────────────────────");
    }
}