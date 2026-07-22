package za.ca.cput.commerce.service;

/*
Author: Joshua Jonathan Bird - 230444032
19/07/2026
 */

import za.ca.cput.commerce.domain.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);
    List<Order> findAll();
    Order findById(String id);
    Order update(String id, Order order);
    void deleteById(String id);
}
