package controller;
/*
   Author: Mogamad Jawaad Allie - 230472125
   Date: 19 july 2026
*/
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
import za.ca.cput.commerce.controller.PaymentController;
import za.ca.cput.commerce.domain.Payment;
import za.ca.cput.commerce.service.PaymentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentController.class)
class PaymentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PaymentService paymentService;

    private Payment samplePayment() {
        return new Payment.Builder()
                .setPaymentId("pay-1")
                .setOrderId("order-1")
                .setPaymentAmount(100.00)
                .setPaymentDate("2026-01-01")
                .setPaymentMethod("CARD")
                .build();
    }

    @Test
    void givenPayments_whenGetAllPayments_thenReturnJsonArray() throws Exception {
        given(paymentService.findAll()).willReturn(List.of(samplePayment()));

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].paymentId", org.hamcrest.Matchers.is("pay-1")));
    }

    @Test
    void givenValidId_whenGetPaymentById_thenReturnPayment() throws Exception {
        given(paymentService.findById("pay-1")).willReturn(samplePayment());

        mockMvc.perform(get("/api/payments/{id}", "pay-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentMethod", org.hamcrest.Matchers.is("CARD")));
    }

    @Test
    void givenInvalidId_whenGetPaymentById_thenReturn404() throws Exception {
        given(paymentService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Payment not found with id: bad-id"));

        mockMvc.perform(get("/api/payments/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidPayment_whenCreatePayment_thenReturn201() throws Exception {
        Payment payment = samplePayment();
        given(paymentService.save(any(Payment.class))).willReturn(payment);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentId", org.hamcrest.Matchers.is("pay-1")));
    }

    @Test
    void givenValidId_whenDeletePayment_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/payments/{id}", "pay-1"))
                .andExpect(status().isNoContent());
    }
}
