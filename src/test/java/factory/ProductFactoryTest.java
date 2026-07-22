/*
 * ProductFactoryTest.java
 * TDD Test for Product Factory
 * Author: Plamedie 230082629
 * Date: 24 March 2026
 */
package factory;

import za.ca.cput.commerce.domain.Product;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.ProductFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFactoryTest {

    @Test
    public void testBuildProductSuccess() {
        Product product = ProductFactory.buildProduct(
                "P001",
                "Laptop",
                "Gaming Laptop",
                999.99
        );

        assertNotNull(product);
        assertEquals("P001", product.getProductId());
        assertEquals("Laptop", product.getProductName());
        assertEquals("Gaming Laptop", product.getDescription());
        assertEquals(999.99, product.getCurrentPrice());
    }

    @Test
    public void testBuildProductNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.buildProduct(null, "Laptop", "Desc", 999.99);
        });
    }

    @Test
    public void testBuildProductEmptyId() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.buildProduct("", "Laptop", "Desc", 999.99);
        });
    }

    @Test
    public void testBuildProductNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.buildProduct("P001", null, "Desc", 999.99);
        });
    }

    @Test
    public void testBuildProductNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.buildProduct("P001", "Laptop", "Desc", -100.00);
        });
    }
}
