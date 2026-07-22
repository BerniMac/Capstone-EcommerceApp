/* NotificationFactoryTest.java
   NotificationFactoryTest class
   Author: Tlangelani Chauke
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.Notification;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.NotificationFactory;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void testCreateNotificationSuccess() {
        Date now = new Date();
        Notification notification = NotificationFactory.createNotification(
                "NOTIF-99",
                "CUST-007",
                "Your order has shipped!",
                now,
                "Sent"
        );

        assertNotNull(notification);
        assertEquals("NOTIF-99", notification.getNotificationId());
        assertEquals("CUST-007", notification.getCustomerId());
        assertEquals("Your order has shipped!", notification.getMessage());
        assertEquals(now, notification.getNotificationDate());
        assertEquals("Sent", notification.getStatus());
    }

    @Test
    void testCreateNotificationFail() {
        Notification notification = NotificationFactory.createNotification(
                "NOTIF-99",
                "",
                "Your order has shipped!",
                null, // invalid date
                "Sent"
        );
        assertNull(notification);
    }
}
