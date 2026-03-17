package com.fooddeliveryapp.service.impl;

import com.fooddeliveryapp.enums.OrderStatus;
import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.enums.PaymentStatus;
import com.fooddeliveryapp.model.cart.CartItem;
import com.fooddeliveryapp.model.discount.Discount;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.order.OrderItem;
import com.fooddeliveryapp.model.payment.Payment;
import com.fooddeliveryapp.model.payment.PaymentResult;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.repository.dbimpl.CartRepository;
import com.fooddeliveryapp.repository.dbimpl.DeliveryAgentRepository;
import com.fooddeliveryapp.repository.dbimpl.DiscountRepository;
import com.fooddeliveryapp.repository.dbimpl.OrderRepository;
import com.fooddeliveryapp.repository.dbimpl.PaymentRepository;
import com.fooddeliveryapp.service.IOrderService;
import com.fooddeliveryapp.service.PaymentProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderServiceImpl implements IOrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final DeliveryAgentRepository deliveryAgentRepository;
    private final DiscountRepository discountRepository;
    private final PaymentProcessor paymentProcessor;

    public OrderServiceImpl(CartRepository cartRepository, OrderRepository orderRepository, PaymentRepository paymentRepository, DeliveryAgentRepository deliveryAgentRepository, DiscountRepository discountRepository, PaymentProcessor paymentProcessor) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.deliveryAgentRepository = deliveryAgentRepository;
        this.discountRepository = discountRepository;
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public Order placeOrder(int customerId, int addressId, PaymentMethod paymentMethod) {

        // 1. get cart items
        List<CartItem> cartItems = cartRepository.getAllCartItem(customerId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2. build order items and calculate total
        List<OrderItem> orderItems = cartItems.stream().map(ci -> new OrderItem(ci.getMenuItemId(), ci.getQuantity(), ci.getPrice())).toList();

        BigDecimal totalAmount = orderItems.stream().map(item -> item.getPriceAtOrder().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. apply discount
        Discount discount = discountRepository.getActiveDiscount();
        double discountRate = 0;
        BigDecimal finalAmount = totalAmount;
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (totalAmount.compareTo(discount.getMinAmount()) >= 0 && discount.getRate() > 0) {
            discountRate = discount.getRate();
            discountAmount = totalAmount.multiply(BigDecimal.valueOf(discountRate / 100.0)).setScale(2, RoundingMode.HALF_UP);
            finalAmount = totalAmount.subtract(discountAmount);
        }

        // 4. give status
        OrderStatus initialStatus = paymentMethod == PaymentMethod.CASH ? OrderStatus.CONFIRMED : OrderStatus.PENDING;

        // 5. create and save order
        Order order = new Order(customerId, 0, totalAmount, addressId, initialStatus, orderItems);
        order.setDiscountRate(discountRate);
        order.setFinalAmount(finalAmount);
        order.setDiscountAmount(discountAmount);

        try {
            orderRepository.placeOrder(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to place order: " + e.getMessage());
        }
        // 6. save payment record as PENDING
        Payment payment = new Payment(order.getId(), paymentMethod, finalAmount);
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.savePayment(payment);

        // 7. handle payment based on method
        if (paymentMethod == PaymentMethod.UPI) {
            PaymentResult result = paymentProcessor.process(PaymentMethod.UPI, finalAmount);

            if (result.isSuccess()) {
                paymentRepository.updatePaymentStatus(payment.getId(), PaymentStatus.SUCCESS);
                orderRepository.updateOrderStatus(order.getId(), OrderStatus.CONFIRMED);
                order.setOrderStatus(OrderStatus.CONFIRMED);

                // assign agent
                assignAgent(order);
                // clear cart
                cartRepository.clearCart(customerId);

            } else {
                paymentRepository.updatePaymentStatus(payment.getId(), PaymentStatus.FAILED);
                orderRepository.updateOrderStatus(order.getId(), OrderStatus.CANCELLED);
                order.setOrderStatus(OrderStatus.CANCELLED);
                throw new RuntimeException("Payment failed: " + result.getMessage());
            }

        } else {
            assignAgent(order);
            cartRepository.clearCart(customerId);
        }
        return order;
    }

    private void assignAgent(Order order) {
        DeliveryAgent agent = deliveryAgentRepository.getAvailableAgent();
        if (agent != null) {
            orderRepository.assignDeliveryAgent(order.getId(), agent.getId());
            deliveryAgentRepository.setAvailability(agent.getId(), false);
            order.setDeliveryAgentId(agent.getId());
        }
    }

    @Override
    public void cancelOrder(int orderId) {
        if (orderId <= 0) throw new IllegalArgumentException("Invalid order ID");
        orderRepository.updateOrderStatus(orderId, OrderStatus.CANCELLED);
    }
}