package za.ca.cput.commerce.dto;

public record PaymentRequest(
        String orderId,
        String cardId,
        double paymentAmount,
        String paymentDate,
        String paymentMethod
) {
}
