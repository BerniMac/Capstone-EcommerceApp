package controller;
/*
import domain.Notification;
import factory.NotificationFactory;
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
import service.NotificationService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NotificationController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {NotificationController.class, NotificationControllerTest.MockServiceConfig.class})
public class NotificationControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public NotificationService notificationService() {
            return org.mockito.Mockito.mock(NotificationService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationService notificationService;

    @Test
    public void testCreate() throws Exception {
        Notification entity = NotificationFactory.createNotification("NOT-101","CUST-101","Order Shipped",new java.util.Date(),"Sent");
        when(notificationService.create(any(Notification.class))).thenReturn(entity);
        String json = "{\"notificationId\":\"NOT-101\",\"customerId\":\"CUST-101\",\"message\":\"Order Shipped\",\"status\":\"Sent\"}";
        mockMvc.perform(post("/notification/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value(entity.getNotificationId()));
    }

    @Test
    public void testRead() throws Exception {
        Notification entity = NotificationFactory.createNotification("NOT-101","CUST-101","Order Shipped",new java.util.Date(),"Sent");
        when(notificationService.read(entity.getNotificationId())).thenReturn(entity);
        mockMvc.perform(get("/notification/read/" + entity.getNotificationId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value(entity.getNotificationId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Notification entity = NotificationFactory.createNotification("NOT-101","CUST-101","Order Shipped",new java.util.Date(),"Sent");
        when(notificationService.update(any(Notification.class))).thenReturn(entity);
        String json = "{\"notificationId\":\"NOT-101\",\"customerId\":\"CUST-101\",\"message\":\"Order Shipped\",\"status\":\"Sent\"}";
        mockMvc.perform(post("/notification/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value(entity.getNotificationId()));
    }

    @Test
    public void testDelete() throws Exception {
        Notification entity = NotificationFactory.createNotification("NOT-101","CUST-101","Order Shipped",new java.util.Date(),"Sent");
        when(notificationService.delete(entity.getNotificationId())).thenReturn(true);
        mockMvc.perform(delete("/notification/delete/" + entity.getNotificationId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Notification entity = NotificationFactory.createNotification("NOT-101","CUST-101","Order Shipped",new java.util.Date(),"Sent");
        when(notificationService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/notification/getall"))
                .andExpect(status().isOk());
    }
}

 */
