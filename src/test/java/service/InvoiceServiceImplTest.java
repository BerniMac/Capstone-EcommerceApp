package service;

/*
Author: Mogamad Jawaad Allie - 230472125
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
import za.ca.cput.commerce.domain.Invoice;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.repository.InvoiceRepository;
import za.ca.cput.commerce.service.impl.InvoiceServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository repository;

    @InjectMocks
    private InvoiceServiceImpl service;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        invoice = new Invoice.Builder()
                .setInvoiceId("INV001")
                .setInvoiceDate(LocalDateTime.now())
                .setTotalAmount(1500.00)
                .setTaxAmount(225.00)
                .setInvoiceStatus("PAID")
                .build();
    }

    @Test
    void create() {
        when(repository.save(invoice)).thenReturn(invoice);

        Invoice created = service.create(invoice);

        assertNotNull(created);
        assertEquals(invoice.getInvoiceId(), created.getInvoiceId());
        verify(repository).save(invoice);
    }

    @Test
    void read() {
        when(repository.findById("INV001"))
                .thenReturn(Optional.of(invoice));

        Invoice found = service.read("INV001");

        assertNotNull(found);
        assertEquals("INV001", found.getInvoiceId());
        verify(repository).findById("INV001");
    }

    @Test
    void update() {
        when(repository.existsById("INV001")).thenReturn(true);
        when(repository.save(invoice)).thenReturn(invoice);

        Invoice updated = service.update(invoice);

        assertNotNull(updated);
        assertEquals("INV001", updated.getInvoiceId());

        verify(repository).existsById("INV001");
        verify(repository).save(invoice);
    }

    @Test
    void delete() {
        when(repository.existsById("INV001")).thenReturn(true);

        boolean deleted = service.delete("INV001");

        assertTrue(deleted);

        verify(repository).existsById("INV001");
        verify(repository).deleteById("INV001");
    }

    @Test
    void getAll() {
        List<Invoice> invoices = List.of(invoice);

        when(repository.findAll()).thenReturn(invoices);

        List<Invoice> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void getByOrderId() {
        when(repository.findByOrderOrderId("ORD001"))
                .thenReturn(Optional.of(invoice));

        Invoice result = service.getByOrderId("ORD001");

        assertNotNull(result);
        assertEquals("INV001", result.getInvoiceId());

        verify(repository).findByOrderOrderId("ORD001");
    }
}