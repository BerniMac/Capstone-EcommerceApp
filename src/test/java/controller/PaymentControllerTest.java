package controller;
/*
import domain.Payment;
import factory.PaymentFactory;
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
import service.PaymentService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PaymentController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {PaymentController.class, PaymentControllerTest.MockServiceConfig.class})
public class PaymentControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public PaymentService paymentService() {
            return org.mockito.Mockito.mock(PaymentService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentService paymentService;

    @Test
    public void testCreate() throws Exception {
        Payment entity = PaymentFactory.createPayment("ORD-101",150.0,"2026-07-19","Credit Card");
        when(paymentService.create(any(Payment.class))).thenReturn(entity);
        String json = "{\"paymentId\":\"PAY-101\",\"orderId\":\"ORD-101\",\"paymentAmount\":150.0,\"paymentDate\":\"2026-07-19\",\"paymentMethod\":\"Credit Card\"}";
        mockMvc.perform(post("/payment/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(entity.getPaymentId()));
    }

    @Test
    public void testRead() throws Exception {
        Payment entity = PaymentFactory.createPayment("ORD-101",150.0,"2026-07-19","Credit Card");
        when(paymentService.read(entity.getPaymentId())).thenReturn(entity);
        mockMvc.perform(get("/payment/read/" + entity.getPaymentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(entity.getPaymentId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Payment entity = PaymentFactory.createPayment("ORD-101",150.0,"2026-07-19","Credit Card");
        when(paymentService.update(any(Payment.class))).thenReturn(entity);
        String json = "{\"paymentId\":\"PAY-101\",\"orderId\":\"ORD-101\",\"paymentAmount\":150.0,\"paymentDate\":\"2026-07-19\",\"paymentMethod\":\"Credit Card\"}";
        mockMvc.perform(post("/payment/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(entity.getPaymentId()));
    }

    @Test
    public void testDelete() throws Exception {
        Payment entity = PaymentFactory.createPayment("ORD-101",150.0,"2026-07-19","Credit Card");
        when(paymentService.delete(entity.getPaymentId())).thenReturn(true);
        mockMvc.perform(delete("/payment/delete/" + entity.getPaymentId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Payment entity = PaymentFactory.createPayment("ORD-101",150.0,"2026-07-19","Credit Card");
        when(paymentService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/payment/getall"))
                .andExpect(status().isOk());
    }
}

 */
