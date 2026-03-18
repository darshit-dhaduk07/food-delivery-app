package com.fooddeliveryapp.service;

import com.fooddeliveryapp.enums.Role;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Admin;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.model.user.User;
import com.fooddeliveryapp.repository.dbimpl.AdminRepository;
import com.fooddeliveryapp.repository.dbimpl.CustomerRepository;
import com.fooddeliveryapp.repository.dbimpl.DeliveryAgentRepository;
import com.fooddeliveryapp.repository.dbimpl.OrderRepository;
import com.fooddeliveryapp.repository.dbimpl.UserRepository;
import com.fooddeliveryapp.validation.UserValidator;

import java.util.List;

public class AuthService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryAgentRepository deliveryAgentRepository;
    private final OrderRepository orderRepository;

    public AuthService(CustomerRepository customerRepository,
                       DeliveryAgentRepository deliveryAgentRepository,
                       AdminRepository adminRepository,
                       OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.deliveryAgentRepository = deliveryAgentRepository;
        this.adminRepository = adminRepository;
        this.orderRepository = orderRepository;
    }

    public Admin registerAdmin(String name, String email, String phone, String password) {
        Admin admin = new Admin(name, email, phone, password, Role.ADMIN);
        UserValidator.validate(admin);
        int id = adminRepository.addAdmin(admin);
        admin.setId(id);
        return admin;
    }

    public Customer registerCustomer(String name, String email,
                                     String phone, String password) {
        Customer customer = new Customer(name, email, phone, password, Role.CUSTOMER);
        UserValidator.validate(customer);
        int id = customerRepository.addCustomer(customer);
        customer.setId(id);
        return customer;
    }

    public DeliveryAgent registerDeliveryAgent(String name, String email,
                                               String phone, String password) {
        DeliveryAgent agent = new DeliveryAgent(name, email, phone, password, Role.DELIVERY_AGENT);
        UserValidator.validate(agent);
        int id = deliveryAgentRepository.addDeliveryAgent(agent);
        agent.setId(id);

        assignPendingOrderIfExists();

        return agent;
    }

    public User login(String email, String password) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email cannot be empty");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be empty");

        User user = UserRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("No account found with this email");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }
        return user;
    }

    private void assignPendingOrderIfExists() {
        List<Order> pendingOrders = orderRepository.getPendingUnassignedOrders();
        if (pendingOrders.isEmpty()) return;

        DeliveryAgent agent = deliveryAgentRepository.getAvailableAgent();
        if (agent == null) return;

        Order nextOrder = pendingOrders.get(0);
        orderRepository.assignDeliveryAgent(nextOrder.getId(), agent.getId());
        deliveryAgentRepository.setAvailability(agent.getId(), false);
        System.out.println("  📦 Order #" + nextOrder.getId()
                + " auto-assigned to agent #" + agent.getId());
    }
}