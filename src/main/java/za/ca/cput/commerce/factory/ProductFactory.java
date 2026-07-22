/*
 * ProductFactory.java
 * Product Factory class
 * Author: Plamedie 230082629
 * Date: 24 March 2026
 */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Product;

public class ProductFactory {

    public static Product buildProduct(String productId, String productName,
                                       String description, double currentPrice) {
        // Validation
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product Name cannot be null or empty");
        }
        if (currentPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        return new Product.Builder()
                .setProductId(productId)
                .setProductName(productName)
                .setDescription(description)
                .setCurrentPrice(currentPrice)
                .build();
    }
}
