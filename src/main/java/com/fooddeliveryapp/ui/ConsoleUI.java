package com.fooddeliveryapp.ui;

import com.fooddeliveryapp.utility.InputHelper;

public class ConsoleUI {

    private final AdminUI adminUI;
    private final CustomerUI customerUI;
    private final DeliveryAgentUI deliveryAgentUI;

    public ConsoleUI(AdminUI adminUI, CustomerUI customerUI,
                     DeliveryAgentUI deliveryAgentUI) {
        this.adminUI = adminUI;
        this.customerUI = customerUI;
        this.deliveryAgentUI = deliveryAgentUI;
    }

    public void start() {
        while (true) {
            System.out.println("\n══════════════════════════════════════");
            System.out.println("       🍔 FOOD DELIVERY APP           ");
            System.out.println("══════════════════════════════════════");
            System.out.println("  1. Admin                            ");
            System.out.println("  2. Customer                         ");
            System.out.println("  3. Delivery Agent                   ");
            System.out.println("  0. Exit                             ");
            System.out.println("══════════════════════════════════════");

            int choice = InputHelper.readInt("Select Option: ");
            switch (choice) {
                case 1 -> adminUI.menu();
                case 2 -> customerUI.menu();
                case 3 -> deliveryAgentUI.menu();
                case 0 -> {
                    System.out.println("\n👋 Goodbye!\n");
                    System.exit(0);
                }
                default -> System.out.println("⚠ Invalid choice.");
            }
        }
    }
}