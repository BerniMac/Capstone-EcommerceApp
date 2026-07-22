/*
 * Product.java
 * Product model class
 * Author: Plamedie 230082629
 * Date: 04 March 2026
 */
package za.ca.cput.commerce.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final String productId;
    private final String productName;
    private final String description;
    private final double currentPrice;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    private Product(Builder builder) {
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.description = builder.description;
        this.currentPrice = builder.currentPrice;
    }

    // Getters
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public double getCurrentPrice() { return currentPrice; }

    // Builder Class
    public static class Builder {
        private String productId;
        private String productName;
        private String description;
        private double currentPrice;

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }
        public Builder copy(Product product) {
            this.productId = product.productId;
            this.productName = product.productName;
            this.description = product.description;
            this.currentPrice = product.currentPrice;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }
}

