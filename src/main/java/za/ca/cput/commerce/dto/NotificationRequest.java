package za.ca.cput.commerce.dto;

public record NotificationRequest(
        String customerId,
        String message,
        String notificationDate,
        String status
) {
}
