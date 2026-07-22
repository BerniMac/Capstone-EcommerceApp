/* PaymentFactory.java
   This is the factory class to create Payment objects.
   Author: Mogamad Jawaad Allie - 230472125
   Date: 25 March 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Payment;
import java.util.UUID;

public class PaymentFactory {
    public static Payment createPayment(String orderId, double paymentAmount, String paymentDate, String paymentMethod) {
        // Basic validation
        if (orderId == null || orderId.isEmpty() || paymentAmount <= 0) {
            return null;
        }

        // Generate a random ID for the payment
        String paymentId = UUID.randomUUID().toString();

        return new Payment.Builder()
                .setPaymentId(paymentId)
                .setOrderId(orderId)
                .setPaymentAmount(paymentAmount)
                .setPaymentDate(paymentDate)
                .setPaymentMethod(paymentMethod)
                .build();
    }
}
