package com.example.messmanagement.controller;

import com.example.messmanagement.model.Order;
import com.example.messmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('Role_student')")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, Authentication authentication) {
        String username = authentication.getName(); // JWT username
        Order createdOrder = orderService.createOrder(username, order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('Role_student')")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('Role_admin')")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update/{orderId}")
    @PreAuthorize("hasRole('Role_admin')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String paymentStatus) {
        Boolean status = Boolean.parseBoolean(paymentStatus); // Convert String to Boolean
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }
}