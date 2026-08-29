package za.ca.cput.commerce.dto;

public record ShipmentRequest(
        String orderId,
        String address,
        String shipmentDate,
        String deliveryDate,
        String status
) {
}
