package com.fooddeliveryapp.ui;

import com.fooddeliveryapp.model.user.Admin;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.DeliveryAgent;

import java.util.Scanner;

public class ConsoleUI {
    private AdminUI adminUI;
    private CustomerUI customerUI;
    private DeliveryAgentUI deliveryAgentUI;

    public ConsoleUI(AdminUI adminUI, CustomerUI customerUI, DeliveryAgentUI deliveryAgentUI) {
        this.adminUI = adminUI;
        this.customerUI = customerUI;
        this.deliveryAgentUI = deliveryAgentUI;
    }

    public void start()
    {
        while(true) {
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Delivery Agent");

            int choice = new Scanner(System.in).nextInt();

            switch(choice) {

                case 1 -> adminUI.menu();

                case 2 -> customerUI.menu();

                case 3 -> deliveryUI.menu();
            }
        }
    }
}
