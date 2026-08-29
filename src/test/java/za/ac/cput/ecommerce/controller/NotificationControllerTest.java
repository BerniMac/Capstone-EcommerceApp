package za.ac.cput.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.NotificationController;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Notification;
import za.ca.cput.commerce.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(SpringExtension.class)
@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Notification notification;

    @BeforeEach
    void setUp() {

        Customer customer = new Customer.Builder()
                .setCustomerId("CUST001")
                .build();

        notification = new Notification.Builder()
                .setNotificationId("NOT001")
                .setCustomer(customer)
                .setMessage("Order has been shipped.")
                .setStatus("UNREAD")
                .setNotificationDate(LocalDateTime.now())
                .build();
    }

    @Test
    void createNotification() throws Exception {

        when(service.create(any(Notification.class)))
                .thenReturn(notification);

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notification)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notificationId").value("NOT001"))
                .andExpect(jsonPath("$.message").value("Order has been shipped."));
    }

    @Test
    void getNotification() throws Exception {

        when(service.read("NOT001")).thenReturn(notification);

        mockMvc.perform(get("/api/notifications/NOT001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value("NOT001"))
                .andExpect(jsonPath("$.status").value("UNREAD"));
    }

    @Test
    void updateNotification() throws Exception {

        when(service.read("NOT001")).thenReturn(notification);
        when(service.update(any(Notification.class))).thenReturn(notification);

        mockMvc.perform(put("/api/notifications/NOT001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value("NOT001"));
    }

    @Test
    void deleteNotification() throws Exception {

        when(service.read("NOT001")).thenReturn(notification);
        doNothing().when(service).delete("NOT001");

        mockMvc.perform(delete("/api/notifications/NOT001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllNotifications() throws Exception {

        when(service.getAll()).thenReturn(List.of(notification));

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].notificationId").value("NOT001"));
    }

    @Test
    void getNotificationsByCustomer() throws Exception {

        when(service.getNotificationsByCustomer("CUST001"))
                .thenReturn(List.of(notification));

        mockMvc.perform(get("/api/notifications/customer/CUST001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message")
                        .value("Order has been shipped."));
    }
}
