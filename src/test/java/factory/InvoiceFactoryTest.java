/* InvoiceFactoryTest.java
   InvoiceFactoryTest class
   Author: Mogamad Jawaad Allie - 230472125
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.Invoice;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.InvoiceFactory;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceFactoryTest {

    @Test
    void testCreateInvoiceSuccess() {
        Invoice invoice = InvoiceFactory.createInvoice(
                "INV-1001",
                "ORD-555",
                "2026-06-21",
                1150.00,
                150.00,
                "Paid"
        );

        assertNotNull(invoice);
        assertEquals("INV-1001", invoice.getInvoiceId());
        assertEquals("ORD-555", invoice.getOrderId());
        assertEquals("2026-06-21", invoice.getInvoiceDate());
        assertEquals(1150.00, invoice.getTotalAmount());
        assertEquals(150.00, invoice.getTaxAmount());
        assertEquals("Paid", invoice.getInvoiceStatus());
    }

    @Test
    void testCreateInvoiceFail() {
        Invoice invoice = InvoiceFactory.createInvoice(
                "INV-1001",
                "ORD-555",
                "2026-06-21",
                -100.00, // invalid amount
                150.00,
                "Paid"
        );
        assertNull(invoice);
    }
}
