package dev.rohu.order_service.service;

import dev.rohu.order_service.dto.request.CreateOrderRequest;
import dev.rohu.order_service.dto.response.OrderResponse;
import dev.rohu.order_service.exception.InvalidStatusTransitionException;
import dev.rohu.order_service.exception.OrderNotFoundException;
import dev.rohu.order_service.model.Order;
import dev.rohu.order_service.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class OrderService {

    private final Map<String, Order> orderStore = new ConcurrentHashMap<>();

    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating order for customer: {}", request.getCustomerName());
        String orderId = UUID.randomUUID().toString();
        Order order = Order.builder()
                .orderId(orderId)
                .customerName(request.getCustomerName())
                .amount(request.getAmount())
                .status(OrderStatus.NEW)
                .build();
        orderStore.put(orderId, order);
        log.info("Order created successfully with id: {}", orderId);
        return OrderResponse.from(order);
    }

    public OrderResponse getOrderById(String orderId) {
        log.info("Fetching order with id: {}", orderId);
        return OrderResponse.from(findOrThrow(orderId));
    }

    public OrderResponse updateOrderStatus(String orderId, OrderStatus newStatus) {
        log.info("Updating order {} status to {}", orderId, newStatus);
        Order order = findOrThrow(orderId);
        validateTransition(order.getStatus(), newStatus);
        order.setStatus(newStatus);
        log.info("Order {} status updated from {} to {}", orderId, order.getStatus(), newStatus);
        return OrderResponse.from(order);
    }

    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders, total count: {}", orderStore.size());
        return orderStore.values().stream()
                .map(OrderResponse::from)
                .toList();
    }

    private Order findOrThrow(String orderId) {
        Order order = orderStore.get(orderId);
        if (order == null) {
            log.error("Order not found with id: {}", orderId);
            throw new OrderNotFoundException(orderId);
        }
        return order;
    }
//git
    private void validateTransition(OrderStatus current, OrderStatus next) {
        boolean valid = switch (current) {
            case NEW -> next == OrderStatus.PROCESSING;
            case PROCESSING -> next == OrderStatus.COMPLETED;
            case COMPLETED -> false;
        };
        if (!valid) {
            log.warn("Invalid status transition attempted from {} to {}", current, next);
            throw new InvalidStatusTransitionException(current, next);
        }
    }

}
//testing DEMO