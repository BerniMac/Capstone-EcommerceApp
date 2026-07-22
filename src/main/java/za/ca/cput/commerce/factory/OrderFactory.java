/* 
  Order____.java
  Author: Joshua Jonathan Bird - 230444032
  Date: 22/03/2026
    */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Order;

public class OrderFactory {
    public static Order createOrder(String orderId, String customerId, String orderDate, double totalAmount) {
        return new Order.Builder()
                .setOrderId(orderId)
                .setCustomerId(customerId)
                .setOrderDate(orderDate)
                .setTotalAmount(totalAmount)
                .build();
    }
}
