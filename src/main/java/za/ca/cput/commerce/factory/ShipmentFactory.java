/*
 * ShipmentFactory.java
 * Author: Tlangelani Chauke
 * Date:22march 2026
 */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Shipment;

import java.util.Date;
import java.util.UUID;

public class ShipmentFactory {

    public static Shipment createShipment(String address,
                                          Date shipmentDate,
                                          Date deliveryDate,
                                          String status) {

        // Validation
        if (address == null || address.isEmpty())
            return null;

        if (shipmentDate == null || deliveryDate == null)
            return null;

        if (status == null || status.isEmpty())
            return null;

        // Generate ID
        String shipmentId = UUID.randomUUID().toString();

        return new Shipment.Builder()
                .setShipmentId(shipmentId)
                .setAddress(address)
                .setShipmentDate(shipmentDate)
                .setDeliveryDate(deliveryDate)
                .setStatus(status)
                .build();
    }
}
