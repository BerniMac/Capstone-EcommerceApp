package service;

/*
Author: Mogamad Jawaad Allie - 230472125
19/07/2026
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
import za.ca.cput.commerce.repository.InvoiceRepository;
import za.ca.cput.commerce.service.impl.InvoiceServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplUnitTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Invoice existingInvoice;

    @BeforeEach
    void setUp() {
        existingInvoice = new Invoice.Builder()
                .setInvoiceId("inv-1")
                .setOrderId("order-1")
                .setInvoiceDate("2026-01-01")
                .setTotalAmount(150.00)
                .setTaxAmount(15.00)
                .setInvoiceStatus("PENDING")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedInvoice() {
        given(invoiceRepository.save(existingInvoice)).willReturn(existingInvoice);

        Invoice saved = invoiceService.save(existingInvoice);

        assertThat(saved).isEqualTo(existingInvoice);
        verify(invoiceRepository, times(1)).save(existingInvoice);
    }

    @Test
    void whenFindAll_thenReturnListOfInvoices() {
        given(invoiceRepository.findAll()).willReturn(List.of(existingInvoice));

        List<Invoice> invoices = invoiceService.findAll();

        assertThat(invoices).hasSize(1).contains(existingInvoice);
    }

    @Test
    void whenValidId_thenInvoiceShouldBeFound() {
        given(invoiceRepository.findById("inv-1")).willReturn(Optional.of(existingInvoice));

        Invoice found = invoiceService.findById("inv-1");

        assertThat(found.getInvoiceId()).isEqualTo("inv-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(invoiceRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> invoiceService.findById("bad-id"));
    }

    @Test
    void whenUpdateStatus_thenRebuiltInvoiceIsSavedWithNewStatus() {
        given(invoiceRepository.findById("inv-1")).willReturn(Optional.of(existingInvoice));
        given(invoiceRepository.save(any(Invoice.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Invoice result = invoiceService.updateStatus("inv-1", "PAID");

        ArgumentCaptor<Invoice> captor = ArgumentCaptor.forClass(Invoice.class);
        verify(invoiceRepository).save(captor.capture());
        Invoice saved = captor.getValue();

        assertThat(saved.getInvoiceId()).isEqualTo("inv-1");
        assertThat(saved.getOrderId()).isEqualTo("order-1");
        assertThat(saved.getInvoiceStatus()).isEqualTo("PAID");
        assertThat(saved.getTotalAmount()).isEqualTo(150.00);
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(invoiceRepository.findById("inv-1")).willReturn(Optional.of(existingInvoice));

        invoiceService.deleteById("inv-1");

        verify(invoiceRepository, times(1)).delete(existingInvoice);
    }
}

