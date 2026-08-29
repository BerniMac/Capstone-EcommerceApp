package service;

/*
Author: Joshua Jonathan Bird - 230444032
1/07/2026
*/
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.repository.OrderItemRepository;
import za.ca.cput.commerce.repository.OrderRepository;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.impl.OrderItemServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemServiceImpl service;

    private Order order;
    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {

        order = new Order.Builder()
                .setOrderId("ORDER001")
                .build();

        product = new Product.Builder()
                .setProductId("PROD001")
                .setProductName("Gaming Laptop")
                .build();

        orderItem = new OrderItem.Builder()
                .setOrderItemId("ITEM001")
                .setOrder(order)
                .setProduct(product)
                .setQuantity(2)
                .setPriceAtPurchase(25000.00)
                .build();
    }

    @Test
    void create() {

        when(orderRepository.findById("ORDER001"))
                .thenReturn(Optional.of(order));

        when(productRepository.findById("PROD001"))
                .thenReturn(Optional.of(product));

        when(orderItemRepository.save(orderItem))
                .thenReturn(orderItem);

        OrderItem created = service.create(orderItem);

        assertNotNull(created);
        assertEquals("ITEM001", created.getOrderItemId());

        verify(orderItemRepository).save(orderItem);
    }

    @Test
    void read() {

        when(orderItemRepository.findById("ITEM001"))
                .thenReturn(Optional.of(orderItem));

        OrderItem found = service.read("ITEM001");

        assertNotNull(found);
        assertEquals("ITEM001", found.getOrderItemId());
    }

    @Test
    void update() {

        when(orderItemRepository.findById("ITEM001"))
                .thenReturn(Optional.of(orderItem));

        when(orderRepository.findById("ORDER001"))
                .thenReturn(Optional.of(order));

        when(productRepository.findById("PROD001"))
                .thenReturn(Optional.of(product));

        when(orderItemRepository.save(any(OrderItem.class)))
                .thenReturn(orderItem);

        OrderItem updated = service.update("ITEM001", orderItem);

        assertNotNull(updated);

        verify(orderItemRepository).save(any(OrderItem.class));
    }

    @Test
    void delete() {

        doNothing().when(orderItemRepository).deleteById("ITEM001");

        service.delete("ITEM001");

        verify(orderItemRepository).deleteById("ITEM001");
    }

    @Test
    void getAll() {

        when(orderItemRepository.findAll())
                .thenReturn(List.of(orderItem));

        List<OrderItem> items = service.getAll();

        assertEquals(1, items.size());
    }

    @Test
    void getByOrder() {

        when(orderItemRepository.findByOrderOrderId("ORDER001"))
                .thenReturn(List.of(orderItem));

        List<OrderItem> items = service.getByOrder("ORDER001");

        assertEquals(1, items.size());
        assertEquals("ORDER001",
                items.get(0).getOrder().getOrderId());
    }

    @Test
    void getByProduct() {

        when(orderItemRepository.findByProductProductId("PROD001"))
                .thenReturn(List.of(orderItem));

        List<OrderItem> items = service.getByProduct("PROD001");

        assertEquals(1, items.size());
        assertEquals("PROD001",
                items.get(0).getProduct().getProductId());
    }
}

