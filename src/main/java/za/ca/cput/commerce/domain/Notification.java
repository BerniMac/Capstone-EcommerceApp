/* Notification.java
   Notification POJO class
   Author: Tlangelani Chauke
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import java.util.Date;
import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final String notificationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private final String customerId;
    private final String message;
    private final Date notificationDate;
    private final String status;

    private Notification(Builder builder) {
        this.notificationId = builder.notificationId;
        this.customerId = builder.customerId;
        this.message = builder.message;
        this.notificationDate = builder.notificationDate;
        this.status = builder.status;
    }

    // Getters
    public String getNotificationId() {
        return notificationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getMessage() {
        return message;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public String getStatus() {
        return status;
    }

    // Builder Class
    public static class Builder {
        private String notificationId;
        private String customerId;
        private String message;
        private Date notificationDate;
        private String status;

        public Builder setNotificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setNotificationDate(Date notificationDate) {
            this.notificationDate = notificationDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder copy(Notification notification) {
            this.notificationId = notification.notificationId;
            this.customerId = notification.customerId;
            this.message = notification.message;
            this.notificationDate = notification.notificationDate;
            this.status = notification.status;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + notificationId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", message='" + message + '\'' +
                ", notificationDate=" + notificationDate +
                ", status='" + status + '\'' +
                '}';
    }
}

