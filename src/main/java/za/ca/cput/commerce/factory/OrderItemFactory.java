/* OrderItemFactory.java
   OrderItemFactory class
   Author: Joshua Jonathan Bird - 230444032
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.OrderItem;

public class OrderItemFactory {

    public static OrderItem createOrderItem(String orderItemId, String orderId, String productId, int quantity, double priceAtPurchase) {
        if (orderItemId == null || orderItemId.isEmpty() || orderId == null || orderId.isEmpty() || productId == null || productId.isEmpty() || quantity <= 0) {
            return null;
        }

        return new OrderItem.Builder()
                .setOrderItemId(orderItemId)
                .setOrderId(orderId)
                .setProductId(productId)
                .setQuantity(quantity)
                .setPriceAtPurchase(priceAtPurchase)
                .build();
    }
}
