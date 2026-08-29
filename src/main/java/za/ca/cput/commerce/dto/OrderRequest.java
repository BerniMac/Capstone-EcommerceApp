package za.ca.cput.commerce.dto;

public record OrderRequest(
        String customerId,
        String orderDate,
        double totalAmount
) {
}
