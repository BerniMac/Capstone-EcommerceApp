package za.ca.cput.commerce.dto;

public record OrderItemRequest(
        String orderId,
        String productId,
        int quantity,
        double priceAtPurchase
) {
}
