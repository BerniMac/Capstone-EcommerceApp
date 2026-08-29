package service;

/*
Author: Tlangelani Chauke
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
import za.ca.cput.commerce.domain.Notification;
import za.ca.cput.commerce.repository.NotificationRepository;
import za.ca.cput.commerce.service.impl.NotificationServiceImpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationServiceImpl service;

    private Notification notification;
    private Customer customer;

    @BeforeEach
    void setUp() {

        customer = new Customer.Builder()
                .setCustomerId("CUST001")
                .build();

        notification = new Notification.Builder()
                .setNotificationId("NOT001")
                .setCustomer(customer)
                .setMessage("Order has been shipped.")
                .setStatus("UNREAD")
                .setNotificationDate(LocalDateTime.now())
                .build();
    }

    @Test
    void create() {

        when(repository.save(notification)).thenReturn(notification);

        Notification created = service.create(notification);

        assertNotNull(created);
        assertEquals("NOT001", created.getNotificationId());

        verify(repository).save(notification);
    }

    @Test
    void read() {

        when(repository.findById("NOT001"))
                .thenReturn(Optional.of(notification));

        Notification found = service.read("NOT001");

        assertNotNull(found);
        assertEquals("Order has been shipped.", found.getMessage());

        verify(repository).findById("NOT001");
    }

    @Test
    void update() {

        when(repository.findById("NOT001"))
                .thenReturn(Optional.of(notification));

        when(repository.save(notification))
                .thenReturn(notification);

        Notification updated = service.update(notification);

        assertNotNull(updated);
        assertEquals("UNREAD", updated.getStatus());

        verify(repository).save(notification);
    }

    @Test
    void delete() {

        when(repository.existsById("NOT001")).thenReturn(true);

        service.delete("NOT001");

        verify(repository).deleteById("NOT001");
    }

    @Test
    void getAll() {

        when(repository.findAll())
                .thenReturn(List.of(notification));

        List<Notification> notifications = service.getAll();

        assertEquals(1, notifications.size());

        verify(repository).findAll();
    }

    @Test
    void getNotificationsByCustomer() {

        when(repository.findByCustomerCustomerId("CUST001"))
                .thenReturn(List.of(notification));

        List<Notification> notifications =
                service.getNotificationsByCustomer("CUST001");

        assertEquals(1, notifications.size());
        assertEquals("Order has been shipped.",
                notifications.getFirst().getMessage());

        verify(repository).findByCustomerCustomerId("CUST001");
    }
}

