/* InventoryFactory.java
   InventoryFactory class
   Author: Plamedie 230082629
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Inventory;

public class InventoryFactory {

    public static Inventory createInventory(String inventoryId, String productId, int stockQuantity, String warehouseLocation, String lastUpdated) {
        if (inventoryId == null || inventoryId.isEmpty() || productId == null || productId.isEmpty() || stockQuantity < 0) {
            return null;
        }

        return new Inventory.Builder()
                .setInventoryId(inventoryId)
                .setProductId(productId)
                .setStockQuantity(stockQuantity)
                .setWarehouseLocation(warehouseLocation)
                .setLastUpdated(lastUpdated)
                .build();
    }
}
