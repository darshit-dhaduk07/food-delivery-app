package com.fooddeliveryapp.ui;

import com.fooddeliveryapp.controller.AuthenticationController;
import com.fooddeliveryapp.controller.DeliveryAgentController;
import com.fooddeliveryapp.enums.OrderStatus;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.model.user.User;
import com.fooddeliveryapp.utility.InputTaker;

import java.util.List;

public class DeliveryAgentUI {

    private final AuthenticationController authController;
    private final DeliveryAgentController deliveryAgentController;
    private DeliveryAgent loggedInAgent;

    public DeliveryAgentUI(AuthenticationController authController,
                           DeliveryAgentController deliveryAgentController) {
        this.authController = authController;
        this.deliveryAgentController = deliveryAgentController;
    }

    // Login

    public void menu() {
        System.out.println("\n┌─── Delivery Agent Login ───────────");
        try {
            String email    = InputTaker.readString("│  Email    : ");
            String password = InputTaker.readString("│  Password : ");
            System.out.println("└────────────────────────────────────");
            User user = authController.login(email, password);
            if (user instanceof DeliveryAgent agent) {
                loggedInAgent = agent;
                System.out.println("\n✅ Welcome, " + agent.getName());
                agentPanel();
            } else {
                System.out.println("❌ This account is not a delivery agent account.");
            }
        } catch (Exception e) {
            System.out.println("└────────────────────────────────────");
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }

    // Main Panel

    private void agentPanel() {
        while (true) {
            String name = loggedInAgent.getName();
            if (name.length() > 24) name = name.substring(0, 21) + "...";

            System.out.println("\n╔══════════════════════════════╗");
            System.out.printf ("║  🚴 %-25s║%n", name);
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Orders                   ║");
            System.out.println("║  2. Earnings                 ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  0. Logout                   ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputTaker.readInt("Select: ");
            switch (choice) {
                case 1 -> ordersPanel();
                case 2 -> viewEarnings();
                case 0 -> {
                    loggedInAgent = null;
                    System.out.println("\n👋 Logged out.");
                    return;
                }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    // Orders Sub-Panel

    private void ordersPanel() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║           ORDERS             ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. View Assigned Orders     ║");
            System.out.println("║  2. Update Order Status      ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  0. Back                     ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputTaker.readInt("Select: ");
            switch (choice) {
                case 1 -> viewAssignedOrders();
                case 2 -> updateOrderStatus();
                case 0 -> { return; }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    // Orders

    private List<Order> viewAssignedOrders() {
        List<Order> orders = deliveryAgentController.viewAssignedOrders(loggedInAgent.getId());
        System.out.println("\n┌─── Assigned Orders ────────────────────────────────────");
        if (orders.isEmpty()) {
            System.out.println("│  No assigned orders.");
        } else {
            System.out.printf("│  %-8s  %-15s  %-12s  %-10s%n",
                    "Order ID", "Status", "Amount", "Customer");
            System.out.println("│  " + "─".repeat(50));
            for (Order o : orders) {
                System.out.printf("│  %-8d  %-15s  ₹%-11s  #%d%n",
                        o.getId(),
                        o.getOrderStatus(),
                        o.getTotalAmount(),
                        o.getCustomerId());
            }
        }
        System.out.println("└────────────────────────────────────────────────────────");
        return orders;
    }

    private void updateOrderStatus() {
        List<Order> orders = viewAssignedOrders();
        if (orders.isEmpty()) return;

        // only show active orders
        List<Order> activeOrders = orders.stream()
                .filter(o -> o.getOrderStatus() != OrderStatus.DELIVERED
                        && o.getOrderStatus() != OrderStatus.CANCELLED)
                .toList();

        if (activeOrders.isEmpty()) {
            System.out.println("⚠ No active orders to update.");
            return;
        }

        try {
            List<Integer> validIds = activeOrders.stream().map(Order::getId).toList();
            int orderId = InputTaker.readIdFromList("Enter Order ID: ", validIds);

            Order selected = activeOrders.stream()
                    .filter(o -> o.getId() == orderId)
                    .findFirst()
                    .orElseThrow();

            System.out.println("\n┌─── Update Status ──────────────────");
            System.out.printf("│  Current : %s%n", selected.getOrderStatus());
            System.out.println("│");
            System.out.println("│  1. PREPARING");
            System.out.println("│  2. ON_THE_WAY");
            System.out.println("│  3. DELIVERED");
            System.out.println("│  4. CANCELLED");
            System.out.println("└────────────────────────────────────");

            int choice = InputTaker.readInt("Select new status: ");
            OrderStatus newStatus = switch (choice) {
                case 1 -> OrderStatus.PREPARING;
                case 2 -> OrderStatus.ON_THE_WAY;
                case 3 -> OrderStatus.DELIVERED;
                case 4 -> OrderStatus.CANCELLED;
                default -> throw new IllegalArgumentException("Invalid status choice.");
            };

            deliveryAgentController.updateOrderStatus(
                    loggedInAgent.getId(), orderId, newStatus);
            System.out.println("✅ Status updated to " + newStatus);

            if (newStatus == OrderStatus.DELIVERED) {
                System.out.println("🎉 Order delivered! Payment collected.");
            }

        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // Earnings

    private void viewEarnings() {
        int delivered = deliveryAgentController.viewEarnings(loggedInAgent.getId());
        List<Order> all = deliveryAgentController.viewAssignedOrders(loggedInAgent.getId());
        int total  = all.size();
        int active = (int) all.stream()
                .filter(o -> o.getOrderStatus() != OrderStatus.DELIVERED
                        && o.getOrderStatus() != OrderStatus.CANCELLED)
                .count();

        System.out.println("\n┌─── Earnings & Stats ───────────────");
        System.out.printf("│  Total Orders Assigned : %d%n", total);
        System.out.printf("│  Orders Delivered      : %d%n", delivered);
        System.out.printf("│  Active Orders         : %d%n", active);
        System.out.println("└────────────────────────────────────");
    }
}