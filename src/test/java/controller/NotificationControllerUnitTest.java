package controller;
/*

   Author: Tlangelani Chauke
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
import za.ca.cput.commerce.controller.NotificationController;
import za.ca.cput.commerce.domain.Notification;
import za.ca.cput.commerce.service.NotificationService;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NotificationController.class)
class NotificationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NotificationService notificationService;

    private Notification sampleNotification(String status) {
        return new Notification.Builder()
                .setNotificationId("notif-1")
                .setCustomerId("cust-1")
                .setMessage("Your order has shipped")
                .setNotificationDate(new Date(0))
                .setStatus(status)
                .build();
    }

    @Test
    void givenNotifications_whenGetAllNotifications_thenReturnJsonArray() throws Exception {
        given(notificationService.findAll()).willReturn(List.of(sampleNotification("UNREAD")));

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].notificationId", org.hamcrest.Matchers.is("notif-1")));
    }

    @Test
    void givenValidId_whenGetNotificationById_thenReturnNotification() throws Exception {
        given(notificationService.findById("notif-1")).willReturn(sampleNotification("UNREAD"));

        mockMvc.perform(get("/api/notifications/{id}", "notif-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", org.hamcrest.Matchers.is("UNREAD")));
    }

    @Test
    void givenInvalidId_whenGetNotificationById_thenReturn404() throws Exception {
        given(notificationService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Notification not found with id: bad-id"));

        mockMvc.perform(get("/api/notifications/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidNotification_whenCreateNotification_thenReturn201() throws Exception {
        Notification notification = sampleNotification("UNREAD");
        given(notificationService.save(any(Notification.class))).willReturn(notification);

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notification)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notificationId", org.hamcrest.Matchers.is("notif-1")));
    }

    @Test
    void givenValidId_whenMarkAsRead_thenReturnUpdatedNotification() throws Exception {
        given(notificationService.markAsRead(eq("notif-1"))).willReturn(sampleNotification("READ"));

        mockMvc.perform(patch("/api/notifications/{id}/read", "notif-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", org.hamcrest.Matchers.is("READ")));
    }

    @Test
    void givenValidId_whenDeleteNotification_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/notifications/{id}", "notif-1"))
                .andExpect(status().isNoContent());
    }
}
