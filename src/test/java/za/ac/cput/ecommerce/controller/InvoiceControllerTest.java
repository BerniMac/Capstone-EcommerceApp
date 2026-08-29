package za.ac.cput.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
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
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.service.InvoiceService;
import za.ca.cput.commerce.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InvoiceService service;

    @Autowired
    private ObjectMapper objectMapper;

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
    void create() throws Exception {

        when(service.create(any(Invoice.class))).thenReturn(invoice);

        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoice)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.invoiceId").value("INV001"))
                .andExpect(jsonPath("$.totalAmount").value(1500.00))
                .andExpect(jsonPath("$.taxAmount").value(225.00))
                .andExpect(jsonPath("$.invoiceStatus").value("PAID"));
    }

    @Test
    void read() throws Exception {

        when(service.read("INV001")).thenReturn(invoice);

        mockMvc.perform(get("/api/invoices/INV001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value("INV001"));
    }

    @Test
    void update() throws Exception {

        when(service.read("INV001")).thenReturn(invoice);
        when(service.update(any(Invoice.class))).thenReturn(invoice);

        mockMvc.perform(put("/api/invoices/INV001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoice)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value("INV001"));
    }

    @Test
    void deleteInvoice() throws Exception {

        doReturn(true).when(service).delete("INV001");

        mockMvc.perform(delete("/api/invoices/INV001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAll() throws Exception {

        when(service.getAll()).thenReturn(List.of(invoice));

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getByOrderId() throws Exception {

        when(service.getByOrderId("ORD001")).thenReturn(invoice);

        mockMvc.perform(get("/api/invoices/order/ORD001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value("INV001"));
    }
}


