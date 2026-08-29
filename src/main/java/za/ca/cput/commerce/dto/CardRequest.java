package za.ca.cput.commerce.dto;

public record CardRequest(
        String productId,
        int stockQuantity,
        String warehouseLocation,
        String lastUpdated
) {
}
