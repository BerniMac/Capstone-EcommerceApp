package service;

/*
Author: Tlangelani Chauke
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Notification;
import za.ca.cput.commerce.repository.NotificationRepository;
import za.ca.cput.commerce.service.impl.NotificationServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplUnitTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification existingNotification;

    @BeforeEach
    void setUp() {
        existingNotification = new Notification.Builder()
                .setNotificationId("notif-1")
                .setCustomerId("cust-1")
                .setMessage("Your order has shipped")
                .setNotificationDate(new Date())
                .setStatus("UNREAD")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedNotification() {
        given(notificationRepository.save(existingNotification)).willReturn(existingNotification);

        Notification saved = notificationService.save(existingNotification);

        assertThat(saved).isEqualTo(existingNotification);
        verify(notificationRepository, times(1)).save(existingNotification);
    }

    @Test
    void whenFindAll_thenReturnListOfNotifications() {
        given(notificationRepository.findAll()).willReturn(List.of(existingNotification));

        List<Notification> notifications = notificationService.findAll();

        assertThat(notifications).hasSize(1).contains(existingNotification);
    }

    @Test
    void whenValidId_thenNotificationShouldBeFound() {
        given(notificationRepository.findById("notif-1")).willReturn(Optional.of(existingNotification));

        Notification found = notificationService.findById("notif-1");

        assertThat(found.getNotificationId()).isEqualTo("notif-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(notificationRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> notificationService.findById("bad-id"));
    }

    @Test
    void whenMarkAsRead_thenRebuiltNotificationIsSavedWithReadStatus() {
        given(notificationRepository.findById("notif-1")).willReturn(Optional.of(existingNotification));
        given(notificationRepository.save(any(Notification.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Notification result = notificationService.markAsRead("notif-1");

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();

        assertThat(saved.getNotificationId()).isEqualTo("notif-1");
        assertThat(saved.getCustomerId()).isEqualTo("cust-1");
        assertThat(saved.getStatus()).isEqualTo("READ");
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(notificationRepository.findById("notif-1")).willReturn(Optional.of(existingNotification));

        notificationService.deleteById("notif-1");

        verify(notificationRepository, times(1)).delete(existingNotification);
    }
}

