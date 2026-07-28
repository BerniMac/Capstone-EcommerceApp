/* CustomerRepositoryTest.java
   Author: 222709006 Qhama dyushu
   Date: 17 July 2026
*/
package repository.impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepository;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    private IRepository<Customer, String> repo;

    @BeforeEach
    void setUp() {
        repo = new CustomerRepositoryImpl();
    }

    private Customer buildCustomer(String id) {
        return CustomerFactory.createCustomer(id, "John Doe", "john@example.com", "0123456789");
    }

    @Test
    void testCreate() {
        assertEquals("CUST-101", repo.create(buildCustomer("CUST-101")).getCustomerId());
    }

    @Test
    void testRead() {
        repo.create(buildCustomer("CUST-101"));
        assertEquals("CUST-101", repo.read("CUST-101").getCustomerId());
    }

    @Test
    void testUpdate() {
        repo.create(buildCustomer("CUST-101"));
        Customer updated = new Customer.Builder().copy(buildCustomer("CUST-101")).setName("Jane Doe").build();
        assertEquals("Jane Doe", repo.update(updated).getName());
    }

    @Test
    void testDelete() {
        repo.create(buildCustomer("CUST-101"));
        assertTrue(repo.delete("CUST-101"));
    }

    @Test
    void testFindAll() {
        repo.create(buildCustomer("CUST-101"));
        repo.create(buildCustomer("CUST-102"));
        assertEquals(2, repo.findAll().size());
    }
}
