package controller;
/*
import domain.Invoice;
import factory.InvoiceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import service.InvoiceService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InvoiceController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {InvoiceController.class, InvoiceControllerTest.MockServiceConfig.class})
public class InvoiceControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public InvoiceService invoiceService() {
            return org.mockito.Mockito.mock(InvoiceService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void testCreate() throws Exception {
        Invoice entity = InvoiceFactory.createInvoice("INV-101","ORD-101","2026-07-19",150.0,22.5,"Paid");
        when(invoiceService.create(any(Invoice.class))).thenReturn(entity);
        String json = "{\"invoiceId\":\"INV-101\",\"orderId\":\"ORD-101\",\"invoiceDate\":\"2026-07-19\",\"totalAmount\":150.0,\"taxAmount\":22.5,\"invoiceStatus\":\"Paid\"}";
        mockMvc.perform(post("/invoice/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value(entity.getInvoiceId()));
    }

    @Test
    public void testRead() throws Exception {
        Invoice entity = InvoiceFactory.createInvoice("INV-101","ORD-101","2026-07-19",150.0,22.5,"Paid");
        when(invoiceService.read(entity.getInvoiceId())).thenReturn(entity);
        mockMvc.perform(get("/invoice/read/" + entity.getInvoiceId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value(entity.getInvoiceId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Invoice entity = InvoiceFactory.createInvoice("INV-101","ORD-101","2026-07-19",150.0,22.5,"Paid");
        when(invoiceService.update(any(Invoice.class))).thenReturn(entity);
        String json = "{\"invoiceId\":\"INV-101\",\"orderId\":\"ORD-101\",\"invoiceDate\":\"2026-07-19\",\"totalAmount\":150.0,\"taxAmount\":22.5,\"invoiceStatus\":\"Paid\"}";
        mockMvc.perform(post("/invoice/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceId").value(entity.getInvoiceId()));
    }

    @Test
    public void testDelete() throws Exception {
        Invoice entity = InvoiceFactory.createInvoice("INV-101","ORD-101","2026-07-19",150.0,22.5,"Paid");
        when(invoiceService.delete(entity.getInvoiceId())).thenReturn(true);
        mockMvc.perform(delete("/invoice/delete/" + entity.getInvoiceId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Invoice entity = InvoiceFactory.createInvoice("INV-101","ORD-101","2026-07-19",150.0,22.5,"Paid");
        when(invoiceService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/invoice/getall"))
                .andExpect(status().isOk());
    }
}

 */
