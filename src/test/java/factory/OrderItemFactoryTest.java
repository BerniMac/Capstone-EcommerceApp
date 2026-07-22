/* OrderItemFactoryTest.java
   OrderItemFactoryTest class
   Author: Joshua Jonathan Bird - 230444032
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.OrderItem;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.OrderItemFactory;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemFactoryTest {

    @Test
    void testCreateOrderItemSuccess() {
        OrderItem item = OrderItemFactory.createOrderItem(
                "ITEM-001",
                "ORD-999",
                "PROD-007",
                2,
                150.00
        );

        assertNotNull(item);
        assertEquals("ITEM-001", item.getOrderItemId());
        assertEquals("ORD-999", item.getOrderId());
        assertEquals("PROD-007", item.getProductId());
        assertEquals(2, item.getQuantity());
        assertEquals(150.00, item.getPriceAtPurchase());
    }

    @Test
    void testCreateOrderItemFail() {
        OrderItem item = OrderItemFactory.createOrderItem(
                "ITEM-001",
                "ORD-999",
                "PROD-007",
                0, // invalid quantity
                150.00
        );
        assertNull(item);
    }
}
