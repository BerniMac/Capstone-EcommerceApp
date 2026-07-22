/* AddressFactory.java
   AddressFactory class
   Author: 222709006 Qhama dyushu
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Address;

public class AddressFactory {

    public static Address createAddress(String addressId, String customerId, String streetAddress, String city, String state, String postalCode, String country, String addressType) {
        if (addressId == null || addressId.isEmpty() || customerId == null || customerId.isEmpty()) {
            return null;
        }

        return new Address.Builder()
                .setAddressId(addressId)
                .setCustomerId(customerId)
                .setStreetAddress(streetAddress)
                .setCity(city)
                .setState(state)
                .setPostalCode(postalCode)
                .setCountry(country)
                .setAddressType(addressType)
                .build();
    }
}
