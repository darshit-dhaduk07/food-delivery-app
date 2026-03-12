package com.fooddeliveryapp.ui;

import com.fooddeliveryapp.controller.AuthenticationController;
import com.fooddeliveryapp.model.user.Customer;

public class CustomerUI {

    private AuthenticationController authenticationController;
    private Customer customer;
    public CustomerUI(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    public void menu() {
        System.out.println("\n══════════════════════════════════════");
        System.out.println("              CUSTOMER");
        System.out.println("══════════════════════════════════════");
        System.out.println("1. Customer Register");
        if (customer == null)
            System.out.println("2. Customer Login");
        else
            System.out.println("2. Customer Panel");
        System.out.println("0. Back");
        System.out.println("══════════════════════════════════════");

        int choice = InputValidator.readInt("Select Option: ");
        switch (choice) {
            case 1 -> auth.registerCustomer();
            case 2 -> {
                if (customer == null)
                {
                    customer = auth.loginCustomer();
                    if(customer == null)
                        break;
                }

                boolean isLogout = customerController.start(customer);
                if (isLogout)
                    customer = null;

            }

            case 0 -> {
                return;
            }
            default -> System.out.println("\nInvalid choice.\n");
        }
    }
}
