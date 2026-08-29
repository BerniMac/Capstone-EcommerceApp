package za.ac.cput.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import za.ca.cput.commerce.controller.OrderController;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;
    private Order order;

    @BeforeEach
    void setUp() {

        customer = new Customer.Builder()
                .setCustomerId("C001")
                .setName("John")
                //.setLastName("Doe")
                .setEmail("john@example.com")
                .build();

        order = new Order.Builder()
                .setOrderId("O001")
                .setCustomer(customer)
                .setOrderDate(LocalDateTime.now())
                .setTotalAmount(2500.00)
                .build();
    }

    @Test
    void create() throws Exception {

        Mockito.when(service.create(any(Order.class)))
                .thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("O001"));
    }

    @Test
    void read() throws Exception {

        Mockito.when(service.read("O001"))
                .thenReturn(order);

        mockMvc.perform(get("/api/orders/O001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("O001"));
    }

    @Test
    void update() throws Exception {

        Mockito.when(service.update(any(Order.class)))
                .thenReturn(order);

        mockMvc.perform(put("/api/orders/O001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("O001"));
    }

    @Test
    void deleteOrder() throws Exception {

        Mockito.when(service.delete("O001"))
                .thenReturn(true);

        mockMvc.perform(delete("/api/orders/O001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAll() throws Exception {

        Mockito.when(service.getAll())
                .thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getOrdersByCustomer() throws Exception {

        Mockito.when(service.getOrdersByCustomer("C001"))
                .thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders/customer/C001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}