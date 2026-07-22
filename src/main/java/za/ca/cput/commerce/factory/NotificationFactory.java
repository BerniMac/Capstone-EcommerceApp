/* NotificationFactory.java
   NotificationFactory class
   Author: Tlangelani Chauke
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Notification;
import java.util.Date;

public class NotificationFactory {

    public static Notification createNotification(String notificationId, String customerId, String message, Date notificationDate, String status) {
        if (notificationId == null || notificationId.isEmpty() || customerId == null || customerId.isEmpty() || message == null || message.isEmpty() || notificationDate == null) {
            return null;
        }

        return new Notification.Builder()
                .setNotificationId(notificationId)
                .setCustomerId(customerId)
                .setMessage(message)
                .setNotificationDate(notificationDate)
                .setStatus(status)
                .build();
    }
}
