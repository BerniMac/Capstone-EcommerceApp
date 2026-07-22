/* InvoiceFactory.java
   InvoiceFactory class
   Author: Mogamad Jawaad Allie - 230472125
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Invoice;

public class InvoiceFactory {

    public static Invoice createInvoice(String invoiceId, String orderId, String invoiceDate, double totalAmount, double taxAmount, String invoiceStatus) {
        if (invoiceId == null || invoiceId.isEmpty() || orderId == null || orderId.isEmpty() || totalAmount < 0) {
            return null;
        }

        return new Invoice.Builder()
                .setInvoiceId(invoiceId)
                .setOrderId(orderId)
                .setInvoiceDate(invoiceDate)
                .setTotalAmount(totalAmount)
                .setTaxAmount(taxAmount)
                .setInvoiceStatus(invoiceStatus)
                .build();
    }
}
