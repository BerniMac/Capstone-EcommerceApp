/* 
  Order____.java
  Author: Joshua Jonathan Bird - 230444032
  Date: 22/03/2026
    */

package factory;

import za.ca.cput.commerce.domain.Order;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.OrderFactory;

import static org.junit.jupiter.api.Assertions.*;

class OrderFactoryTest {
    @Test
    void testCreateOrder() {
        Order order = OrderFactory.createOrder("O001", "C123", "2026-03-19", 1500.00);
        assertNotNull(order);
        assertEquals("O001", order.getOrderId());
        assertEquals("C123", order.getCustomerId());
        assertEquals(1500.00, order.getTotalAmount());
    }
}

