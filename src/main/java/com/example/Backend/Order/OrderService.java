package com.example.Backend.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public void createOrder(Order order) {
        orderRepository.save(order);
    }
    public Order findById(long id) {
        return orderRepository.findById(id).get();
    }
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
