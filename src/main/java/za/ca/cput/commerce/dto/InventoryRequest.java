package za.ca.cput.commerce.dto;

public record InventoryRequest(
        String productId,
        int stockQuantity,
        String warehouseLocation,
        String lastUpdated
) {
}
