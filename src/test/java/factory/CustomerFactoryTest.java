/* CustomerFactoryTest.java
   Author: 222709006 Qhama dyushu
   Date: 17 July 2026
*/
package factory;

import domain.Customer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerFactoryTest {

    @Test
    void testCreateCustomerSuccess() {
        Customer customer = CustomerFactory.createCustomer(
                "CUST-101", "John Doe", "john@example.com", "0123456789"
        );

        assertNotNull(customer);
        assertEquals("CUST-101", customer.getCustomerId());
        assertEquals("John Doe", customer.getName());
        assertEquals("john@example.com", customer.getEmail());
        assertEquals("0123456789", customer.getPhone());
    }

    @Test
    void testCreateCustomerFail() {
        Customer customer = CustomerFactory.createCustomer(
                null, "John Doe", "john@example.com", "0123456789"
        );
        assertNull(customer);
    }
}
