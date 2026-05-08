package com.foodorder.service;

import com.foodorder.dto.OrderRequest;
import com.foodorder.model.*;
import com.foodorder.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final FoodItemRepository foodItemRepository;

    @Transactional
    public Order placeOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));

        Order order = Order.builder()
                .customer(customer)
                .status(Order.OrderStatus.PENDING)
                .orderTime(LocalDateTime.now())
                .deliveryAddress(request.getDeliveryAddress() != null
                        ? request.getDeliveryAddress()
                        : customer.getAddress())
                .totalAmount(0.0)
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = request.getItems().stream().map(itemReq -> {
            FoodItem foodItem = foodItemRepository.findById(itemReq.getFoodItemId())
                    .orElseThrow(() -> new RuntimeException("Food item not found: " + itemReq.getFoodItemId()));

            if (!foodItem.isAvailable()) {
                throw new RuntimeException("Food item not available: " + foodItem.getName());
            }

            return OrderItem.builder()
                    .order(savedOrder)
                    .foodItem(foodItem)
                    .quantity(itemReq.getQuantity())
                    .price(foodItem.getPrice())
                    .build();
        }).collect(Collectors.toList());

        savedOrder.setOrderItems(orderItems);

        double total = orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        savedOrder.setTotalAmount(total);

        Order finalOrder = orderRepository.save(savedOrder);

        log.info("╔══════════════════════════════════════════╗");
        log.info("║       NAYA ORDER PLACE HUA!              ║");
        log.info("╠══════════════════════════════════════════╣");
        log.info("║  Order ID    : {}", finalOrder.getId());
        log.info("║  Customer    : {}", customer.getName());
        log.info("║  Phone       : {}", customer.getPhone());
        log.info("║  Address     : {}", finalOrder.getDeliveryAddress());
        log.info("║  Order Time  : {}", finalOrder.getOrderTime().format(formatter));
        log.info("╠══════════════════════════════════════════╣");
        log.info("║  ORDERED ITEMS:");
        for (OrderItem item : orderItems) {
            log.info("║    -> {} x{} = Rs.{}",
                    item.getFoodItem().getName(),
                    item.getQuantity(),
                    (int)(item.getPrice() * item.getQuantity()));
        }
        log.info("╠══════════════════════════════════════════╣");
        log.info("║  TOTAL AMOUNT : Rs. {}", finalOrder.getTotalAmount().intValue());
        log.info("║  STATUS       : {}", finalOrder.getStatus());
        log.info("╚══════════════════════════════════════════╝");

        return finalOrder;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        log.info("--- Sabhi Orders ({} total) ---", orders.size());
        for (Order o : orders) {
            log.info("  Order #{} | {} | Rs.{} | {}",
                    o.getId(), o.getCustomer().getName(),
                    o.getTotalAmount().intValue(), o.getStatus());
        }
        return orders;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByStatus(String status) {
        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        return orderRepository.findByStatus(orderStatus);
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        Order.OrderStatus oldStatus = order.getStatus();
        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        Order updated = orderRepository.save(order);
        log.info("Order #{} status update: {} --> {}", id, oldStatus, updated.getStatus());
        return updated;
    }

    @Transactional
    public Order cancelOrder(Long id) {
        Order order = getOrderById(id);
        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel a delivered order");
        }
        order.setStatus(Order.OrderStatus.CANCELLED);
        Order cancelled = orderRepository.save(order);
        log.info("Order #{} CANCEL hua | Customer: {}", id, order.getCustomer().getName());
        return cancelled;
    }
}