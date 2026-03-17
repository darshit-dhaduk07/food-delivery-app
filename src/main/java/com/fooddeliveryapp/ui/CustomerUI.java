package com.fooddeliveryapp.ui;

import com.fooddeliveryapp.controller.AuthenticationController;
import com.fooddeliveryapp.controller.CustomerController;
import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.model.cart.CartItem;
import com.fooddeliveryapp.model.menu.MenuItem;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Address;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.User;
import com.fooddeliveryapp.utility.InputHelper;

import java.util.List;

public class CustomerUI {

    private final AuthenticationController authController;
    private final CustomerController customerController;
    private Customer loggedInCustomer;

    public CustomerUI(AuthenticationController authController,
                      CustomerController customerController) {
        this.authController = authController;
        this.customerController = customerController;
    }

    // ─── Main Menu ───────────────────────────────────────────────────────────

    public void menu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║          CUSTOMER            ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Register                 ║");
            System.out.println("║  2. Login                    ║");
            System.out.println("║  0. Back                     ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputHelper.readInt("Select: ");
            switch (choice) {
                case 1 -> register();
                case 2 -> {
                    login();
                    if (loggedInCustomer != null) customerPanel();
                }
                case 0 -> { return; }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    // ─── Auth ────────────────────────────────────────────────────────────────

    private void register() {
        System.out.println("\n┌─── Register ───────────────────────");
        String name     = InputHelper.readString("│  Name     : ");
        String email    = InputHelper.readString("│  Email    : ");
        String phone    = InputHelper.readString("│  Phone    : ");
        String password = InputHelper.readString("│  Password : ");
        System.out.println("└────────────────────────────────────");
        try {
            Customer c = authController.registerCustomer(name, email, phone, password);
            System.out.println("\n✅ Registration successful! Welcome, " + c.getName());
        } catch (Exception e) {
            System.out.println("\n❌ Registration failed: " + e.getMessage());
        }
    }

    private void login() {
        System.out.println("\n┌─── Login ──────────────────────────");
        String email    = InputHelper.readString("│  Email    : ");
        String password = InputHelper.readString("│  Password : ");
        System.out.println("└────────────────────────────────────");
        try {
            User user = authController.login(email, password);
            if (user instanceof Customer c) {
                loggedInCustomer = c;
                System.out.println("\n✅ Welcome back, " + c.getName() + "!");
            } else {
                System.out.println("\n❌ This account is not a customer account.");
            }
        } catch (Exception e) {
            System.out.println("\n❌ Login failed: " + e.getMessage());
        }
    }

    // ─── Customer Panel ──────────────────────────────────────────────────────

    private void customerPanel() {
        while (true) {
            // trim name to fit box
            String name = loggedInCustomer.getName();
            if (name.length() > 24) name = name.substring(0, 21) + "...";

            System.out.println("\n╔══════════════════════════════╗");
            System.out.printf ("║  👤 %-25s║%n", name);
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  ── Menu ─────────────────  ║");
            System.out.println("║   1. View Menu               ║");
            System.out.println("║  ── Cart ─────────────────  ║");
            System.out.println("║   2. View Cart               ║");
            System.out.println("║   3. Add Item to Cart        ║");
            System.out.println("║   4. Remove from Cart        ║");
            System.out.println("║   5. Update Quantity         ║");
            System.out.println("║  ── Address ──────────────  ║");
            System.out.println("║   6. Add Address             ║");
            System.out.println("║   7. View Addresses          ║");
            System.out.println("║  ── Orders ───────────────  ║");
            System.out.println("║   8. Place Order             ║");
            System.out.println("║   9. Order History           ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║   0. Logout                  ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = InputHelper.readInt("Select: ");
            switch (choice) {
                case 1 -> viewMenu();
                case 2 -> viewCart();
                case 3 -> addToCart();
                case 4 -> removeFromCart();
                case 5 -> updateCartQuantity();
                case 6 -> addAddress();
                case 7 -> viewAddresses();
                case 8 -> placeOrder();
                case 9 -> viewOrderHistory();
                case 0 -> {
                    loggedInCustomer = null;
                    System.out.println("\n👋 Logged out successfully.");
                    return;
                }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }

    // ─── Menu ────────────────────────────────────────────────────────────────

    private void viewMenu() {
        List<MenuItem> items = customerController.getAvailableMenuItems();
        System.out.println("\n┌─── Available Menu ──────────────────────────────");
        if (items.isEmpty()) {
            System.out.println("│  No items available.");
        } else {
            System.out.printf("│  %-5s  %-25s  %-10s%n", "ID", "Name", "Price");
            System.out.println("│  " + "─".repeat(44));
            for (MenuItem item : items) {
                System.out.printf("│  %-5d  %-25s  ₹%s%n",
                        item.getId(), item.getName(), item.getPrice());
            }
        }
        System.out.println("└────────────────────────────────────────────────");
    }

    // ─── Cart ────────────────────────────────────────────────────────────────

    private void viewCart() {
        List<CartItem> items = customerController.viewCart(loggedInCustomer.getId());
        System.out.println("\n┌─── Your Cart ───────────────────────────────────");
        if (items.isEmpty()) {
            System.out.println("│  Your cart is empty.");
        } else {
            System.out.printf("│  %-8s  %-12s  %-6s  %-10s%n",
                    "Item ID", "Menu ID", "Qty", "Price");
            System.out.println("│  " + "─".repeat(42));
            for (CartItem item : items) {
                System.out.printf("│  %-8d  %-12d  %-6d  ₹%s%n",
                        item.getId(), item.getMenuItemId(),
                        item.getQuantity(), item.getPrice());
            }
        }
        System.out.println("└────────────────────────────────────────────────");
    }

    private void addToCart() {
        List<MenuItem> items = customerController.getAvailableMenuItems();
        if (items.isEmpty()) {
            System.out.println("⚠ No items available.");
            return;
        }
        viewMenu();
        try {
            List<Integer> validIds = items.stream().map(MenuItem::getId).toList();
            int menuItemId = InputHelper.readIdFromList("Enter Menu Item ID : ", validIds);
            int quantity   = InputHelper.readPositiveInt("Enter Quantity     : ");
            customerController.addToCart(loggedInCustomer.getId(), menuItemId, quantity);
            System.out.println("✅ Item added to cart.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void removeFromCart() {
        List<CartItem> items = customerController.viewCart(loggedInCustomer.getId());
        if (items.isEmpty()) {
            System.out.println("⚠ Your cart is empty.");
            return;
        }
        viewCart();
        try {
            List<Integer> validIds = items.stream().map(CartItem::getId).toList();
            int cartItemId = InputHelper.readIdFromList("Enter Cart Item ID to remove: ", validIds);
            customerController.removeFromCart(cartItemId);
            System.out.println("✅ Item removed.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void updateCartQuantity() {
        List<CartItem> items = customerController.viewCart(loggedInCustomer.getId());
        if (items.isEmpty()) {
            System.out.println("⚠ Your cart is empty.");
            return;
        }
        viewCart();
        try {
            List<Integer> validIds = items.stream().map(CartItem::getId).toList();
            int cartItemId = InputHelper.readIdFromList("Enter Cart Item ID : ", validIds);
            int quantity   = InputHelper.readPositiveInt("New Quantity       : ");
            customerController.updateCartQuantity(cartItemId, quantity);
            System.out.println("✅ Quantity updated.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ─── Address ─────────────────────────────────────────────────────────────

    private void addAddress() {
        System.out.println("\n┌─── Add Address ────────────────────");
        try {
            String addressName = InputHelper.readString("│  Address: ");
            System.out.println("└────────────────────────────────────");
            customerController.addAddress(loggedInCustomer.getId(), addressName);
            System.out.println("✅ Address saved.");
        } catch (Exception e) {
            System.out.println("└────────────────────────────────────");
            System.out.println("❌ " + e.getMessage());
        }
    }

    private List<Address> viewAddresses() {
        List<Address> addresses = customerController.getAddresses(loggedInCustomer.getId());
        System.out.println("\n┌─── Your Addresses ─────────────────────────────");
        if (addresses.isEmpty()) {
            System.out.println("│  No addresses saved yet.");
        } else {
            System.out.printf("│  %-5s  %-35s%n", "ID", "Address");
            System.out.println("│  " + "─".repeat(42));
            for (Address a : addresses) {
                System.out.printf("│  %-5d  %s%n", a.getId(), a.getAddressName());
            }
        }
        System.out.println("└────────────────────────────────────────────────");
        return addresses;
    }

    // ─── Order ───────────────────────────────────────────────────────────────

    private void placeOrder() {
        // Step 1 — address
        List<Address> addresses = viewAddresses();
        if (addresses.isEmpty()) {
            System.out.println("⚠ Please add an address first.");
            return;
        }

        try {
            List<Integer> validAddressIds = addresses.stream().map(Address::getId).toList();
            int addressId = InputHelper.readIdFromList("Select Address ID: ", validAddressIds);

            // Step 2 — cart
            List<CartItem> cartItems = customerController.viewCart(loggedInCustomer.getId());
            if (cartItems.isEmpty()) {
                System.out.println("⚠ Cart is empty. Add items before placing order.");
                return;
            }
            viewCart();

            // Step 3 — payment
            System.out.println("\n┌─── Payment Method ─────────────────");
            System.out.println("│  1. UPI");
            System.out.println("│  2. Cash on Delivery");
            System.out.println("└────────────────────────────────────");
            int payChoice = InputHelper.readInt("Select: ");

            PaymentMethod paymentMethod = switch (payChoice) {
                case 1  -> PaymentMethod.UPI;
                case 2  -> PaymentMethod.CASH;
                default -> throw new IllegalArgumentException("Invalid payment choice.");
            };

            // Step 4 — confirmation summary
            Address selectedAddress = addresses.stream()
                    .filter(a -> a.getId() == addressId)
                    .findFirst()
                    .orElseThrow();

            System.out.println("\n┌─── Order Summary ──────────────────");
            System.out.printf("│  Address  : %s%n", selectedAddress.getAddressName());
            System.out.printf("│  Payment  : %s%n", paymentMethod);
            System.out.printf("│  Items    : %d item(s) in cart%n", cartItems.size());
            System.out.println("└────────────────────────────────────");

            String confirm = InputHelper.readString("Confirm order? (yes/no): ");
            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("⚠ Order cancelled.");
                return;
            }

            // Step 5 — place order
            Order order = customerController.placeOrder(
                    loggedInCustomer.getId(), addressId, paymentMethod);

            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║     ✅ ORDER PLACED SUCCESSFULLY!    ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.printf ("║  Order ID     : %-20d║%n", order.getId());
            System.out.printf ("║  Total Amount : ₹%-19s║%n", order.getTotalAmount());
            System.out.printf ("║  Discount     : %-19s║%n", order.getDiscountRate() + "%");
            System.out.printf ("║  Final Amount : ₹%-19s║%n",
                    order.getFinalAmount() != null ? order.getFinalAmount() : order.getTotalAmount());
            System.out.printf ("║  Status       : %-20s║%n", order.getOrderStatus());
            System.out.printf ("║  Payment      : %-20s║%n", paymentMethod);
            System.out.println("╚══════════════════════════════════════╝");

        } catch (Exception e) {
            System.out.println("\n❌ " + e.getMessage());
        }
    }

    private void viewOrderHistory() {
        List<Order> orders = customerController.viewOrderHistory(loggedInCustomer.getId());
        System.out.println("\n┌─── Order History ──────────────────────────────────────");
        if (orders.isEmpty()) {
            System.out.println("│  No orders found.");
        } else {
            System.out.printf("│  %-8s  %-15s  %-12s  %-12s%n",
                    "Order ID", "Status", "Total", "Final");
            System.out.println("│  " + "─".repeat(52));
            for (Order o : orders) {
                System.out.printf("│  %-8d  %-15s  ₹%-11s  ₹%s%n",
                        o.getId(),
                        o.getOrderStatus(),
                        o.getTotalAmount(),
                        o.getFinalAmount() != null ? o.getFinalAmount() : o.getTotalAmount());
            }
        }
        System.out.println("└────────────────────────────────────────────────────────");
    }
}