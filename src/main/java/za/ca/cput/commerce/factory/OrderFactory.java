/* 
  Order____.java
  Author: Joshua Jonathan Bird - 230444032
  Date: 22/03/2026
    */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Order;

import java.time.LocalDateTime;

public class OrderFactory {

    public static Order createOrder(Customer customer,
                                    LocalDateTime orderDate,
                                    double totalAmount) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative.");
        }

        return new Order.Builder()
                .setCustomer(customer)
                .setOrderDate(orderDate)
                .setTotalAmount(totalAmount)
                .build();
    }
}
