package za.ca.cput.commerce.controller;

/*
Author: Joshua Jonathan Bird - 230444032
19/07/2026
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@PathVariable String id) {
        return orderItemService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.save(orderItem);
    }

    @PutMapping("/{id}")
    public OrderItem updateOrderItem(@PathVariable String id, @RequestBody OrderItem orderItem) {
        return orderItemService.update(id, orderItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItem(@PathVariable String id) {
        orderItemService.deleteById(id);
    }
}

