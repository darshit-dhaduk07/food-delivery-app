package com.fooddeliveryapp;

import com.fooddeliveryapp.controller.AdminController;
import com.fooddeliveryapp.controller.AuthenticationController;
import com.fooddeliveryapp.controller.CustomerController;
import com.fooddeliveryapp.controller.DeliveryAgentController;
import com.fooddeliveryapp.repository.dbimpl.*;
import com.fooddeliveryapp.service.*;
import com.fooddeliveryapp.service.impl.*;
import com.fooddeliveryapp.ui.*;

public class App {
    private static void addAdmin(AdminRepository adminRepository, AuthService authService) {
        try {
            authService.registerAdmin("Darshit", "darshitdhaduk@food.com", "8000824129", "admin@123");
            System.out.println("Default admin created.");
        } catch (Exception e) {
            System.out.println("Seed admin skipped: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        //repo
        UserRepository userRepository = new UserRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        DeliveryAgentRepository deliveryAgentRepository = new DeliveryAgentRepository();
        AdminRepository adminRepository = new AdminRepository();
        CartRepository cartRepository = new CartRepository();
        MenuRepository menuRepository = new MenuRepository();
        OrderRepository orderRepository = new OrderRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        DiscountRepository discountRepository = new DiscountRepository();

        //service
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        AuthService authService = new AuthService(
                customerRepository,
                deliveryAgentRepository,
                adminRepository,
                orderRepository
        );

        IOrderService orderService = new OrderServiceImpl(
                cartRepository,
                orderRepository,
                paymentRepository,
                deliveryAgentRepository,
                discountRepository,
                paymentProcessor
        );

        ICustomerService customerService = new CustomerServiceImpl(
                cartRepository,
                customerRepository,
                menuRepository,
                orderRepository,
                orderService
        );

        IAdminService adminService = new AdminServiceImpl(
                menuRepository,
                deliveryAgentRepository,
                orderRepository,
                discountRepository,
                authService
        );

        IDeliveryAgentService deliveryAgentService = new DeliveryAgentServiceImpl(
                orderRepository,
                deliveryAgentRepository,
                paymentRepository,
                paymentProcessor
        );

        // controller
        AuthenticationController authController = new AuthenticationController(authService);
        CustomerController customerController = new CustomerController(customerService);
        AdminController adminController = new AdminController(adminService);
        DeliveryAgentController deliveryAgentController = new DeliveryAgentController(deliveryAgentService);

        // ui
        CustomerUI customerUI = new CustomerUI(authController, customerController);
        AdminUI adminUI = new AdminUI(authController, adminController);
        DeliveryAgentUI deliveryAgentUI = new DeliveryAgentUI(authController, deliveryAgentController);

        ConsoleUI consoleUI = new ConsoleUI(adminUI, customerUI, deliveryAgentUI);

        addAdmin(adminRepository, authService);

        // start
        consoleUI.start();
    }
}