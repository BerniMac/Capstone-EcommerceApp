package za.ca.cput.commerce.service;

/*
Author: Joshua Jonathan Bird - 230444032
19/07/2026
 */

import za.ca.cput.commerce.domain.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);
    List<OrderItem> findAll();
    OrderItem findById(String id);
    OrderItem update(String id, OrderItem orderItem);
    void deleteById(String id);
}