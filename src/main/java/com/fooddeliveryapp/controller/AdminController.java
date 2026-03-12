package com.fooddeliveryapp.controller;

import com.fooddeliveryapp.model.user.User;

public class AdminController {
    public boolean start(User manager) {

        while (true) {

            System.out.println("\n══════════════════════════════════════");
            System.out.println("            MANAGER PANEL");
            System.out.println("══════════════════════════════════════");
            System.out.println("1. Menu Management");
            System.out.println("2. Order Management");
            System.out.println("3. Customer Management");
            System.out.println("4. Delivery Management");
            System.out.println("5. Discount Scheme");
            System.out.println("6. Back to Previous Menu");
            System.out.println("0. Logout");
            System.out.println("══════════════════════════════════════");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> menuSection();

                case 2 -> orderSection();

                case 3 -> customerSection();

                case 4 -> deliverySection();
                case 5 -> discountScheme();
                case 6 -> {
                    return false;
                }
                case 0 -> {
                    System.out.println("\nLog Out...\n");
                    return true;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void menuSection() {

        while (true) {

            System.out.println("\n──────── MENU MANAGEMENT ────────");
            System.out.println("1. Show Menu");
            System.out.println("2. Add Category");
            System.out.println("3. Add Item to Category");
            System.out.println("4. Update Item Price");
            System.out.println("5. Change Availability");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerService.showMenu();

                case 2 -> {
                    String name = InputValidator.readStringOnlyLetters("Category Name: ");
                    managerService.addCategory(name);
                }

                case 3 -> {
                    managerService.showMenu();
                    int cid = InputValidator.readPositiveInt("Category ID: ");
                    String itemName = InputValidator.readString("Item Name: ");
                    double price = InputValidator.readPositiveDouble("Price: ");
                    if(price > 1000)
                    {
                        System.out.println("Item Price should be less than 1000");
                        break;
                    }
                    managerService.addItemToCategory(cid, itemName, price);
                }

                case 4 -> {
                    managerService.showMenu();
                    int id = InputValidator.readPositiveInt("Item ID: ");
                    double price = InputValidator.readPositiveDouble("New Price: ");
                    if(price > 1000)
                    {
                        System.out.println("Item Price should be less than 1000");
                        break;
                    }
                    managerService.updatePrice(id, price);
                }

                case 5 -> {
                    managerService.showAllMenu();
                    int id = InputValidator.readPositiveInt("Enter ID: ");
                    boolean status = InputValidator.readBoolean("Available");
                    managerService.changeAvailability(id, status);
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void orderSection() {

        while (true) {

            System.out.println("\n──────── ORDER MANAGEMENT ────────");
            System.out.println("1. Show All Orders");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> {
                    System.out.printf("%-5s %-15s %-20s %-15s %-12s %-10s%n", "ID", "CUSTOMER", "ADDRESS", "DELIVERY_AGENT", "STATUS", "TOTAL");
                    System.out.println("──────────────────────────────────────────────────────────────────────────");
                    managerService.showAllOrders();
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void customerSection() {

        while (true) {

            System.out.println("\n──────── CUSTOMER MANAGEMENT ────────");
            System.out.println("1. Show Customers");
            System.out.println("2. Remove Customer");
            System.out.println("3. Send Notification to Customer");
            System.out.println("4. Send Notification to ALL Customers");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> {
                    managerService.showAllCustomers();
                }

                case 2 -> {
                    managerService.showAllCustomers();
                    int id = InputValidator.readInt("Customer ID: ");
                    managerService.removeCustomer(id);
                }
                case 3 -> {
                    managerService.showAllCustomers();
                    int id =
                            InputValidator.readPositiveInt("Customer ID: ");

                    String msg =
                            InputValidator.readMessage("Message: ");

                    managerService.notifyCustomer(id, msg);
                }
                case 4 -> {

                    String msg =
                            InputValidator.readMessage("Message: ");

                    managerService.notifyAllCustomers(msg);
                }
                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }


    private void deliverySection() {

        while (true) {

            System.out.println("\n──────── DELIVERY MANAGEMENT ────────");
            System.out.println("1. Show Delivery Agents");
            System.out.println("2. Add Delivery Agent");
            System.out.println("3. Remove Delivery Agent");
            System.out.println("4. Notify Delivery Agent");
            System.out.println("5. Notify All Delivery Agents");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerService.showAllDeliveryAgents();

                case 2 -> {
                    DeliveryAgent agent = authenticationService.registerDeliveryAgent();
                    if(agent != null)
                        managerService.addDeliveryAgent(agent);
                }

                case 3 -> {
                    managerService.showAllDeliveryAgents();
                    int id = InputValidator.readInt("DeliveryAgent ID: ");
                    managerService.removeDeliveryAgent(id);
                }
                case 4 -> {
                    managerService.showAllDeliveryAgents();
                    int id =
                            InputValidator.readPositiveInt("DeliveryAgent ID: ");

                    String msg =
                            InputValidator.readMessage("Message: ");

                    managerService.notifyDeliveryAgent(id, msg);
                }
                case 5 -> {

                    String msg =
                            InputValidator.readMessage("Message: ");

                    managerService.notifyAllDeliveryAgents(msg);
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void discountScheme() {
        while (true) {
            System.out.println("\n──────── SCHEME MANAGEMENT ────────");
            System.out.println("1. See Current Scheme");
            System.out.println("2. Update Scheme");
            System.out.println("0. Back");

            int choice = InputValidator.readInt("Select Option: ");

            switch (choice) {
                case 1 -> managerService.showDiscount();
                case 2 -> managerService.updateDiscount();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid Choice");
            }
        }
    }
}
