package service;

/*
2026/07/12
Author: Tlangelani Chauke
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
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.repository.ShipmentRepository;
import za.ca.cput.commerce.service.impl.ShipmentServiceImpl;

import java.time.LocalDate;
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
class ShipmentServiceImplTest {

    @Mock
    private ShipmentRepository repository;

    @InjectMocks
    private ShipmentServiceImpl service;

    private Shipment shipment;
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order.Builder()
                .setOrderId("ORD001")
                .build();

        shipment = new Shipment.Builder()
                .setShipmentId("SHIP001")
                .setAddress("123 Main Street")
                .setShipmentDate(LocalDateTime.now())
                .setDeliveryDate(LocalDateTime.now().plusDays(3))
                .setStatus("Shipped")
                .setOrder(order)
                .build();
    }

    @Test
    void create() {
        when(repository.save(shipment)).thenReturn(shipment);

        Shipment created = service.create(shipment);

        assertNotNull(created);
        assertEquals("SHIP001", created.getShipmentId());
        verify(repository).save(shipment);
    }

    @Test
    void read() {
        when(repository.findById("SHIP001"))
                .thenReturn(Optional.of(shipment));

        Shipment found = service.read("SHIP001");

        assertNotNull(found);
        assertEquals("SHIP001", found.getShipmentId());
        verify(repository).findById("SHIP001");
    }

    @Test
    void update() {
        when(repository.existsById("SHIP001")).thenReturn(true);
        when(repository.save(shipment)).thenReturn(shipment);

        Shipment updated = service.update(shipment);

        assertNotNull(updated);
        assertEquals("Shipped", updated.getStatus());
        verify(repository).existsById("SHIP001");
        verify(repository).save(shipment);
    }

    @Test
    void update_NotFound() {
        when(repository.existsById("SHIP001")).thenReturn(false);

        Shipment updated = service.update(shipment);

        assertNull(updated);
        verify(repository).existsById("SHIP001");
        verify(repository, never()).save(any());
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById("SHIP001");

        service.delete("SHIP001");

        verify(repository).deleteById("SHIP001");
    }

    @Test
    void getAll() {
        List<Shipment> shipments = List.of(shipment);

        when(repository.findAll()).thenReturn(shipments);

        List<Shipment> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void findByOrderId() {
        when(repository.findByOrderOrderId("ORD001"))
                .thenReturn(Optional.of(shipment));

        Shipment found = service.findByOrderId("ORD001");

        assertNotNull(found);
        assertEquals("ORD001", found.getOrder().getOrderId());
        verify(repository).findByOrderOrderId("ORD001");
    }

    @Test
    void findByOrderId_NotFound() {
        when(repository.findByOrderOrderId("ORD001"))
                .thenReturn(Optional.empty());

        Shipment found = service.findByOrderId("ORD001");

        assertNull(found);
        verify(repository).findByOrderOrderId("ORD001");
    }
}