package controller;
/*
import domain.Order;
import factory.OrderFactory;
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
import service.OrderService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {OrderController.class, OrderControllerTest.MockServiceConfig.class})
public class OrderControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public OrderService orderService() {
            return org.mockito.Mockito.mock(OrderService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Test
    public void testCreate() throws Exception {
        Order entity = OrderFactory.createOrder("ORD-101","CUST-101","2026-07-19",150.0);
        when(orderService.create(any(Order.class))).thenReturn(entity);
        String json = "{\"orderId\":\"ORD-101\",\"customerId\":\"CUST-101\",\"orderDate\":\"2026-07-19\",\"totalAmount\":150.0}";
        mockMvc.perform(post("/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(entity.getOrderId()));
    }

    @Test
    public void testRead() throws Exception {
        Order entity = OrderFactory.createOrder("ORD-101","CUST-101","2026-07-19",150.0);
        when(orderService.read(entity.getOrderId())).thenReturn(entity);
        mockMvc.perform(get("/order/read/" + entity.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(entity.getOrderId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Order entity = OrderFactory.createOrder("ORD-101","CUST-101","2026-07-19",150.0);
        when(orderService.update(any(Order.class))).thenReturn(entity);
        String json = "{\"orderId\":\"ORD-101\",\"customerId\":\"CUST-101\",\"orderDate\":\"2026-07-19\",\"totalAmount\":150.0}";
        mockMvc.perform(post("/order/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(entity.getOrderId()));
    }

    @Test
    public void testDelete() throws Exception {
        Order entity = OrderFactory.createOrder("ORD-101","CUST-101","2026-07-19",150.0);
        when(orderService.delete(entity.getOrderId())).thenReturn(true);
        mockMvc.perform(delete("/order/delete/" + entity.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Order entity = OrderFactory.createOrder("ORD-101","CUST-101","2026-07-19",150.0);
        when(orderService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/order/getall"))
                .andExpect(status().isOk());
    }
}

 */
