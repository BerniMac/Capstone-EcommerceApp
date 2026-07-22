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
import za.ca.cput.commerce.controller.OrderItemController;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.service.OrderItemService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderItemController.class)
class OrderItemControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderItemService orderItemService;

    private OrderItem sampleOrderItem() {
        return new OrderItem.Builder()
                .setOrderItemId("item-1")
                .setOrderId("order-1")
                .setProductId("prod-1")
                .setQuantity(2)
                .setPriceAtPurchase(49.99)
                .build();
    }

    @Test
    void givenOrderItems_whenGetAllOrderItems_thenReturnJsonArray() throws Exception {
        given(orderItemService.findAll()).willReturn(List.of(sampleOrderItem()));

        mockMvc.perform(get("/api/order-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].orderItemId", org.hamcrest.Matchers.is("item-1")));
    }

    @Test
    void givenValidId_whenGetOrderItemById_thenReturnOrderItem() throws Exception {
        given(orderItemService.findById("item-1")).willReturn(sampleOrderItem());

        mockMvc.perform(get("/api/order-items/{id}", "item-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", org.hamcrest.Matchers.is(2)));
    }

    @Test
    void givenInvalidId_whenGetOrderItemById_thenReturn404() throws Exception {
        given(orderItemService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("OrderItem not found with id: bad-id"));

        mockMvc.perform(get("/api/order-items/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidOrderItem_whenCreateOrderItem_thenReturn201() throws Exception {
        OrderItem orderItem = sampleOrderItem();
        given(orderItemService.save(any(OrderItem.class))).willReturn(orderItem);

        mockMvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderItemId", org.hamcrest.Matchers.is("item-1")));
    }

    @Test
    void givenValidIdAndOrderItem_whenUpdateOrderItem_thenReturnUpdatedOrderItem() throws Exception {
        OrderItem updated = sampleOrderItem();
        given(orderItemService.update(eq("item-1"), any(OrderItem.class))).willReturn(updated);

        mockMvc.perform(put("/api/order-items/{id}", "item-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId", org.hamcrest.Matchers.is("item-1")));
    }

    @Test
    void givenValidId_whenDeleteOrderItem_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/order-items/{id}", "item-1"))
                .andExpect(status().isNoContent());
    }
}