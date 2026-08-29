package za.ac.cput.ecommerce.controller;
/*
   Author: Mogamad Jawaad Allie - 230472125
   Date: 19 july 2026
*/
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import za.ca.cput.commerce.controller.PaymentController;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Payment;
import za.ca.cput.commerce.service.CardService;
import za.ca.cput.commerce.service.OrderService;
import za.ca.cput.commerce.service.PaymentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
    private PaymentService service;

    // NEW: needed because the controller now resolves orderId -> Order and cardId -> Card
    private Payment payment;
    private Order order;
    private Card card;

    @BeforeEach
    void setUp() {

        order = new Order.Builder()
                .setOrderId("ORD001")
                .build();

        card = new Card.Builder()
                .setCardId("CARD001")
                .build();

        payment = new Payment.Builder()
                .setPaymentId("PAY001")
                .setOrder(order)
                .setPaymentAmount(2500)
                .setPaymentMethod("Credit Card")
                .setCard(card)
                .build();
    }

    @Test
    void create() throws Exception {

        when(service.create(any(Payment.class)))
                .thenReturn(payment);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value("PAY001"));

        verify(service).create(any(Payment.class));
    }

    @Test
    void getAll() throws Exception {

        when(service.getAll())
                .thenReturn(List.of(payment));

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].paymentId").value("PAY001"));

        verify(service).getAll();
    }

    @Test
    void getById() throws Exception {

        when(service.getById("PAY001"))
                .thenReturn(payment);

        mockMvc.perform(get("/api/payments/PAY001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value("PAY001"));

        verify(service).getById("PAY001");
    }

    @Test
    void update() throws Exception {

        when(service.update(eq("PAY001"), any(Payment.class)))
                .thenReturn(payment);

        mockMvc.perform(put("/api/payments/PAY001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value("PAY001"));

        verify(service).update(eq("PAY001"), any(Payment.class));
    }

    @Test
    void delete() throws Exception {

        doNothing().when(service).delete("PAY001");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/payments/PAY001"))
                .andExpect(status().isNoContent());

        verify(service).delete("PAY001");
    }
    @Test
    void getByCard() throws Exception {

        when(service.getByCard("CARD001"))
                .thenReturn(List.of(payment));

        mockMvc.perform(get("/api/payments/card/CARD001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].paymentId").value("PAY001"));

        verify(service).getByCard("CARD001");
    }
    @Test
    void getByOrder() throws Exception {

        when(service.getByOrder("ORD001"))
                .thenReturn(payment);

        mockMvc.perform(get("/api/payments/order/ORD001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value("PAY001"));

        verify(service).getByOrder("ORD001");
    }
}


