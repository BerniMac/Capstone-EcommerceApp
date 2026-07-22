package za.ca.cput.commerce.service.impl;

/*
Author: Joshua Jonathan Bird - 230444032
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.repository.OrderItemRepository;
import za.ca.cput.commerce.service.OrderItemService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem findById(String id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem"+ id));
    }

    @Override
    public OrderItem update(String id, OrderItem orderItem) {
        OrderItem existing = findById(id);

        OrderItem updated = new OrderItem.Builder()
                .copy(existing)
                .setQuantity(orderItem.getQuantity())
                .setPriceAtPurchase(orderItem.getPriceAtPurchase())
                .build();
        return orderItemRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        orderItemRepository.delete(findById(id));
    }
}