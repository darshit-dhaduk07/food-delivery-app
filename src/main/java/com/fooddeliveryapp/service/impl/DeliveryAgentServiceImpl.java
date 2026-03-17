package com.fooddeliveryapp.service.impl;

import com.fooddeliveryapp.enums.OrderStatus;
import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.enums.PaymentStatus;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.payment.Payment;
import com.fooddeliveryapp.model.payment.PaymentResult;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.repository.dbimpl.DeliveryAgentRepository;
import com.fooddeliveryapp.repository.dbimpl.OrderRepository;
import com.fooddeliveryapp.repository.dbimpl.PaymentRepository;
import com.fooddeliveryapp.service.IDeliveryAgentService;
import com.fooddeliveryapp.service.PaymentProcessor;

import java.util.List;

public class DeliveryAgentServiceImpl implements IDeliveryAgentService {

    private final OrderRepository orderRepository;
    private final DeliveryAgentRepository deliveryAgentRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentProcessor paymentProcessor;

    public DeliveryAgentServiceImpl(OrderRepository orderRepository, DeliveryAgentRepository deliveryAgentRepository, PaymentRepository paymentRepository, PaymentProcessor paymentProcessor) {
        this.orderRepository = orderRepository;
        this.deliveryAgentRepository = deliveryAgentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public List<Order> viewAssignedOrders(int agentId) {
        if (agentId <= 0) throw new IllegalArgumentException("Invalid agent ID");
        return orderRepository.getOrdersByDeliveryAgentId(agentId);
    }

    @Override
    public void updateOrderStatus(int agentId, int orderId, OrderStatus status) {
        if (agentId <= 0) throw new IllegalArgumentException("Invalid agent ID");
        if (orderId <= 0) throw new IllegalArgumentException("Invalid order ID");
        if (status == null) throw new IllegalArgumentException("Status cannot be null");

        orderRepository.updateOrderStatus(orderId, status);

        if (status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED) {

            if (status == OrderStatus.DELIVERED) {
                Payment payment = paymentRepository.getPaymentByOrderId(orderId);
                if (payment != null && payment.getPaymentMethod() == PaymentMethod.CASH) {
                    PaymentResult result = paymentProcessor.process(PaymentMethod.CASH, payment.getAmount());
                    if (result.isSuccess()) {
                        paymentRepository.updatePaymentStatus(payment.getId(), PaymentStatus.SUCCESS);
                    }
                }
            }


            deliveryAgentRepository.setAvailability(agentId, true);

            assignPendingOrderIfExists();
        }
    }

    @Override
    public int viewEarnings(int agentId) {
        if (agentId <= 0) throw new IllegalArgumentException("Invalid agent ID");
        return (int) orderRepository.getOrdersByDeliveryAgentId(agentId).stream().filter(o -> o.getOrderStatus() == OrderStatus.DELIVERED).count();
    }

    private void assignPendingOrderIfExists() {
        List<Order> pendingOrders = orderRepository.getPendingUnassignedOrders();
        if (pendingOrders.isEmpty()) return;

        DeliveryAgent agent = deliveryAgentRepository.getAvailableAgent();
        if (agent == null) return;

        Order nextOrder = pendingOrders.get(0);
        orderRepository.assignDeliveryAgent(nextOrder.getId(), agent.getId());
        deliveryAgentRepository.setAvailability(agent.getId(), false);
        System.out.println("  📦 Order #" + nextOrder.getId() + " auto-assigned to agent #" + agent.getId());
    }
}