package za.ca.cput.commerce.service.impl;

/*
Author: Joshua Jonathan Bird - 230444032
19/07/2026
 */

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.repository.OrderRepository;
import za.ca.cput.commerce.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order"+ id));
    }

    @Override
    public Order update(String id, Order order) {
        Order existing = findById(id);

        Order updated = new Order.Builder()
                //.copy(existing)
                .setTotalAmount(order.getTotalAmount())
                .build();
        return orderRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        orderRepository.delete(findById(id));
    }
}
