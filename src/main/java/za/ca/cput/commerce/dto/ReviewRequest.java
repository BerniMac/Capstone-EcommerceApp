package za.ca.cput.commerce.dto;

public record ReviewRequest(
        String customerId,
                             String productId,
                             int rating,
                             String comment,
                             String reviewDate) {
}
