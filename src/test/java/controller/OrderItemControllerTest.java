package controller;
/*
import domain.OrderItem;
import factory.OrderItemFactory;
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
import service.OrderItemService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderItemController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {OrderItemController.class, OrderItemControllerTest.MockServiceConfig.class})
public class OrderItemControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public OrderItemService orderItemService() {
            return org.mockito.Mockito.mock(OrderItemService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderItemService orderItemService;

    @Test
    public void testCreate() throws Exception {
        OrderItem entity = OrderItemFactory.createOrderItem("ITEM-101","ORD-101","PROD-101",2,75.0);
        when(orderItemService.create(any(OrderItem.class))).thenReturn(entity);
        String json = "{\"orderItemId\":\"ITEM-101\",\"orderId\":\"ORD-101\",\"productId\":\"PROD-101\",\"quantity\":2,\"priceAtPurchase\":75.0}";
        mockMvc.perform(post("/orderitem/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId").value(entity.getOrderItemId()));
    }

    @Test
    public void testRead() throws Exception {
        OrderItem entity = OrderItemFactory.createOrderItem("ITEM-101","ORD-101","PROD-101",2,75.0);
        when(orderItemService.read(entity.getOrderItemId())).thenReturn(entity);
        mockMvc.perform(get("/orderitem/read/" + entity.getOrderItemId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId").value(entity.getOrderItemId()));
    }

    @Test
    public void testUpdate() throws Exception {
        OrderItem entity = OrderItemFactory.createOrderItem("ITEM-101","ORD-101","PROD-101",2,75.0);
        when(orderItemService.update(any(OrderItem.class))).thenReturn(entity);
        String json = "{\"orderItemId\":\"ITEM-101\",\"orderId\":\"ORD-101\",\"productId\":\"PROD-101\",\"quantity\":2,\"priceAtPurchase\":75.0}";
        mockMvc.perform(post("/orderitem/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId").value(entity.getOrderItemId()));
    }

    @Test
    public void testDelete() throws Exception {
        OrderItem entity = OrderItemFactory.createOrderItem("ITEM-101","ORD-101","PROD-101",2,75.0);
        when(orderItemService.delete(entity.getOrderItemId())).thenReturn(true);
        mockMvc.perform(delete("/orderitem/delete/" + entity.getOrderItemId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        OrderItem entity = OrderItemFactory.createOrderItem("ITEM-101","ORD-101","PROD-101",2,75.0);
        when(orderItemService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/orderitem/getall"))
                .andExpect(status().isOk());
    }
}

 */
