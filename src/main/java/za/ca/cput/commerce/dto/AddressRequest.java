package za.ca.cput.commerce.dto;

public record AddressRequest(
        String customerId,
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country,
        String addressType
) {
}
