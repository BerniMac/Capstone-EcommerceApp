package controller;
/*

  Author: Joshua Jonathan Bird - 230444032
  Date: 19/07/2026
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
import za.ca.cput.commerce.controller.OrderController;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.service.OrderService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    private Order sampleOrder() {
        return new Order.Builder()
                .setOrderId("order-1")
                .setCustomerId("cust-1")
                .setOrderDate("2026-01-01")
                .setTotalAmount(100.00)
                .build();
    }

    @Test
    void givenOrders_whenGetAllOrders_thenReturnJsonArray() throws Exception {
        given(orderService.findAll()).willReturn(List.of(sampleOrder()));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].orderId", org.hamcrest.Matchers.is("order-1")));
    }

    @Test
    void givenValidId_whenGetOrderById_thenReturnOrder() throws Exception {
        given(orderService.findById("order-1")).willReturn(sampleOrder());

        mockMvc.perform(get("/api/orders/{id}", "order-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount", org.hamcrest.Matchers.is(100.00)));
    }

    @Test
    void givenInvalidId_whenGetOrderById_thenReturn404() throws Exception {
        given(orderService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Order not found with id: bad-id"));

        mockMvc.perform(get("/api/orders/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidOrder_whenCreateOrder_thenReturn201() throws Exception {
        Order order = sampleOrder();
        given(orderService.save(any(Order.class))).willReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId", org.hamcrest.Matchers.is("order-1")));
    }

    @Test
    void givenValidIdAndOrder_whenUpdateOrder_thenReturnUpdatedOrder() throws Exception {
        Order updated = sampleOrder();
        given(orderService.update(eq("order-1"), any(Order.class))).willReturn(updated);

        mockMvc.perform(put("/api/orders/{id}", "order-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", org.hamcrest.Matchers.is("order-1")));
    }

    @Test
    void givenValidId_whenDeleteOrder_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/orders/{id}", "order-1"))
                .andExpect(status().isNoContent());
    }
}