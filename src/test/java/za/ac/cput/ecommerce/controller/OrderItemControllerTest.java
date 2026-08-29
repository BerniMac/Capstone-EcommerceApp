package za.ac.cput.ecommerce.controller;
/*

  Author: Joshua Jonathan Bird - 230444032
  Date: 19/07/2026
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
import za.ca.cput.commerce.controller.OrderItemController;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.service.OrderItemService;
import za.ca.cput.commerce.service.OrderService;
import za.ca.cput.commerce.service.ProductService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderItemController.class)
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderItemService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;
    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {

        order = new Order.Builder()
                .setOrderId("ORDER001")
                .build();

        product = new Product.Builder()
                .setProductId("PROD001")
                .setProductName("Gaming Laptop")
                .build();

        orderItem = new OrderItem.Builder()
                .setOrderItemId("ITEM001")
                .setOrder(order)
                .setProduct(product)
                .setQuantity(2)
                .setPriceAtPurchase(25000.00)
                .build();
    }

    @Test
    void create() throws Exception {

        when(service.create(any(OrderItem.class)))
                .thenReturn(orderItem);

        mockMvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId").value("ITEM001"));
    }

    @Test
    void read() throws Exception {

        when(service.read("ITEM001"))
                .thenReturn(orderItem);

        mockMvc.perform(get("/api/order-items/ITEM001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId").value("ITEM001"));
    }

    @Test
    void update() throws Exception {

        when(service.update(eq("ITEM001"), any(OrderItem.class)))
                .thenReturn(orderItem);

        mockMvc.perform(put("/api/order-items/ITEM001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId").value("ITEM001"));
    }

    @Test
    void deleteOrderItem() throws Exception {

        when(service.read("ITEM001")).thenReturn(orderItem);
        doNothing().when(service).delete("ITEM001");

        mockMvc.perform(delete("/api/order-items/ITEM001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAll() throws Exception {

        when(service.getAll())
                .thenReturn(List.of(orderItem));

        mockMvc.perform(get("/api/order-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderItemId").value("ITEM001"));
    }

    @Test
    void getByOrder() throws Exception {

        when(service.getByOrder("ORDER001"))
                .thenReturn(List.of(orderItem));

        mockMvc.perform(get("/api/order-items/order/ORDER001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderItemId").value("ITEM001"));
    }

    @Test
    void getByProduct() throws Exception {

        when(service.getByProduct("PROD001"))
                .thenReturn(List.of(orderItem));

        mockMvc.perform(get("/api/order-items/product/PROD001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderItemId").value("ITEM001"));
    }
}


