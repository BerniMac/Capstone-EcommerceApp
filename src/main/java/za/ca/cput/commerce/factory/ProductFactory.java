/*
 * ProductFactory.java
 * Product Factory class
 * Author: Plamedie 230082629
 * Date: 24 March 2026
 */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Product;
public class ProductFactory {

    private ProductFactory() {
    }

    public static Product createProduct(String productName,
                                        String description,
                                        double currentPrice) {

        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name is required.");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required.");
        }

        if (currentPrice <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }

        return new Product.Builder()
                .setProductName(productName)
                .setDescription(description)
                .setCurrentPrice(currentPrice)
                .build();
    }
}
