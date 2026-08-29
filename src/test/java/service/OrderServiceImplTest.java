package service;

/*
Author: Joshua Jonathan Bird - 230444032
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.repository.OrderRepository;
import za.ca.cput.commerce.service.impl.OrderServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderServiceImpl service;

    private Customer customer;
    private Order order;

    @BeforeEach
    void setUp() {

        customer = new Customer.Builder()
                .setCustomerId("C001")
                .setName("John")
                //.setLastName("Doe")
                .setEmail("john@example.com")
                .build();

        order = new Order.Builder()
                .setOrderId("O001")
                .setCustomer(customer)
                .setOrderDate(LocalDateTime.now())
                .setTotalAmount(2500.00)
                .build();
    }

    @Test
    void create() {

        when(repository.save(order)).thenReturn(order);

        Order created = service.create(order);

        assertNotNull(created);
        assertEquals("O001", created.getOrderId());

        verify(repository).save(order);
    }

    @Test
    void read() {

        when(repository.findById("O001"))
                .thenReturn(Optional.of(order));

        Order found = service.read("O001");

        assertNotNull(found);
        assertEquals("O001", found.getOrderId());

        verify(repository).findById("O001");
    }

    @Test
    void update() {

        when(repository.existsById("O001"))
                .thenReturn(true);

        when(repository.save(order))
                .thenReturn(order);

        Order updated = service.update(order);

        assertNotNull(updated);
        assertEquals(2500.00, updated.getTotalAmount());

        verify(repository).existsById("O001");
        verify(repository).save(order);
    }

    @Test
    void delete() {

        when(repository.existsById("O001"))
                .thenReturn(true);

        boolean deleted = service.delete("O001");

        assertTrue(deleted);

        verify(repository).existsById("O001");
        verify(repository).deleteById("O001");
    }

    @Test
    void getAll() {

        when(repository.findAll())
                .thenReturn(List.of(order));

        List<Order> orders = service.getAll();

        assertEquals(1, orders.size());

        verify(repository).findAll();
    }

    @Test
    void getOrdersByCustomer() {

        when(repository.findByCustomerCustomerId("C001"))
                .thenReturn(List.of(order));

        List<Order> orders =
                service.getOrdersByCustomer("C001");

        assertEquals(1, orders.size());

        verify(repository)
                .findByCustomerCustomerId("C001");
    }
}