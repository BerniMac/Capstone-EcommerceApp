/*
 * ShipmentFactoryTest.java
 * Author: Tlangelani Chauke
 * Date:22march 2026
 */
package factory;

import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.factory.ShipmentFactory;

import static org.junit.jupiter.api.Assertions.*;

        import java.util.Date;

public class ShipmentFactoryTest {

    @Test
    void createShipment() {
        Shipment shipment = ShipmentFactory.createShipment(
                "Cape Town",
                new Date(),
                new Date(),
                "Shipped"
        );

        assertNotNull(shipment);
        assertNotNull(shipment.getShipmentId());
    }
}
