/* PaymentFactoryTest.java
   This is the test class for PaymentFactory.
   Author: Mogamad Jawaad Allie - 230472125
   Date: 25 March 2026
*/
package factory;

import za.ca.cput.commerce.domain.Payment;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.PaymentFactory;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFactoryTest {
    @Test
    void testCreatePayment() {
        Payment payment = PaymentFactory.createPayment("ORD-123", 500.00, "2026-03-25", "Credit Card");
        
        // Assertions
        assertNotNull(payment);
        assertNotNull(payment.getPaymentId()); // ID should be generated
        assertEquals("ORD-123", payment.getOrderId());
        assertEquals(500.00, payment.getPaymentAmount());
        assertEquals("Credit Card", payment.getPaymentMethod());
        
        System.out.println("Payment Created: " + payment.toString());
    }

    @Test
    void testCreatePaymentWithFail() {
        // Test with invalid amount
        Payment payment = PaymentFactory.createPayment("ORD-123", -10.00, "2026-03-25", "Cash");
        assertNull(payment);
    }
}
