package za.ca.cput.commerce.service.impl;

/*
Author: Tlangelani Chauke
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Notification;
import za.ca.cput.commerce.repository.NotificationRepository;
import za.ca.cput.commerce.service.NotificationService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(String id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification"+ id));
    }

    @Override
    public Notification markAsRead(String id) {
        Notification existing = findById(id);

        Notification updated = new Notification.Builder()
                .copy(existing)
                .setStatus("READ")
                .build();
        return notificationRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        notificationRepository.delete(findById(id));
    }
}
