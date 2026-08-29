package za.ca.cput.commerce.dto;

public record InvoiceRequest(
        String orderId,
        String invoiceDate,
        double totalAmount,
        double taxAmount,
        String invoiceStatus
) {
}
