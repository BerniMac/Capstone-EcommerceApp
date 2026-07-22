/* Inventory.java
   Inventory POJO class
   Author: Plamedie 230082629
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import jakarta.persistence.*;
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final String inventoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private final String productId;
    private final int stockQuantity;
    private final String warehouseLocation;
    private final String lastUpdated;

    private Inventory(Builder builder) {
        this.inventoryId = builder.inventoryId;
        this.productId = builder.productId;
        this.stockQuantity = builder.stockQuantity;
        this.warehouseLocation = builder.warehouseLocation;
        this.lastUpdated = builder.lastUpdated;
    }

    // Getters
    public String getInventoryId() {
        return inventoryId;
    }

    public String getProductId() {
        return productId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    // Builder Class
    public static class Builder {
        private String inventoryId;
        private String productId;
        private int stockQuantity;
        private String warehouseLocation;
        private String lastUpdated;

        public Builder setInventoryId(String inventoryId) {
            this.inventoryId = inventoryId;
            return this;
        }

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder setStockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder setWarehouseLocation(String warehouseLocation) {
            this.warehouseLocation = warehouseLocation;
            return this;
        }

        public Builder setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder copy(Inventory inventory) {
            this.inventoryId = inventory.inventoryId;
            this.productId = inventory.productId;
            this.stockQuantity = inventory.stockQuantity;
            this.warehouseLocation = inventory.warehouseLocation;
            this.lastUpdated = inventory.lastUpdated;
            return this;
        }

        public Inventory build() {
            return new Inventory(this);
        }
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId='" + inventoryId + '\'' +
                ", productId='" + productId + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", warehouseLocation='" + warehouseLocation + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}