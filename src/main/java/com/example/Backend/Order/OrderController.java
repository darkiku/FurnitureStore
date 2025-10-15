package com.example.Backend.Order;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/save")
    public ResponseEntity<Order> save(@Valid @RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Order> findById(Long id){
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Order>> findAll(){
    List<Order> orders = orderService.findAll();
    return ResponseEntity.ok(orders);
    }
}
