package za.ca.cput.commerce.service;



import za.ca.cput.commerce.domain.Notification;

import java.util.List;

public interface NotificationService {
    Notification save(Notification notification);
    List<Notification> findAll();
    Notification findById(String id);
    Notification markAsRead(String id);
    void deleteById(String id);
}
