package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.InvoiceController;
import za.ca.cput.commerce.domain.Invoice;
import za.ca.cput.commerce.service.InvoiceService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvoiceController.class)
class InvoiceControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InvoiceService invoiceService;

    private Invoice sampleInvoice() {
        return new Invoice.Builder()
                .setInvoiceId("inv-1")
                .setOrderId("order-1")
                .setInvoiceDate("2026-01-01")
                .setTotalAmount(150.00)
                .setTaxAmount(15.00)
                .setInvoiceStatus("PENDING")
                .build();
    }

    @Test
    void givenInvoices_whenGetAllInvoices_thenReturnJsonArray() throws Exception {
        given(invoiceService.findAll()).willReturn(List.of(sampleInvoice()));

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].invoiceId", org.hamcrest.Matchers.is("inv-1")));
    }

    @Test
    void givenValidId_whenGetInvoiceById_thenReturnInvoice() throws Exception {
        given(invoiceService.findById("inv-1")).willReturn(sampleInvoice());

        mockMvc.perform(get("/api/invoices/{id}", "inv-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceStatus", org.hamcrest.Matchers.is("PENDING")));
    }

    @Test
    void givenInvalidId_whenGetInvoiceById_thenReturn404() throws Exception {
        given(invoiceService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Invoice not found with id: bad-id"));

        mockMvc.perform(get("/api/invoices/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidInvoice_whenCreateInvoice_thenReturn201() throws Exception {
        Invoice invoice = sampleInvoice();
        given(invoiceService.save(any(Invoice.class))).willReturn(invoice);

        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoice)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.invoiceId", org.hamcrest.Matchers.is("inv-1")));
    }

    @Test
    void givenValidIdAndStatus_whenUpdateInvoiceStatus_thenReturnUpdatedInvoice() throws Exception {
        Invoice updated = new Invoice.Builder()
                .setInvoiceId("inv-1")
                .setOrderId("order-1")
                .setInvoiceDate("2026-01-01")
                .setTotalAmount(150.00)
                .setTaxAmount(15.00)
                .setInvoiceStatus("PAID")
                .build();
        given(invoiceService.updateStatus(eq("inv-1"), eq("PAID"))).willReturn(updated);

        mockMvc.perform(patch("/api/invoices/{id}/status", "inv-1")
                        .param("status", "PAID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceStatus", org.hamcrest.Matchers.is("PAID")));
    }

    @Test
    void givenValidId_whenDeleteInvoice_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/invoices/{id}", "inv-1"))
                .andExpect(status().isNoContent());
    }
}
