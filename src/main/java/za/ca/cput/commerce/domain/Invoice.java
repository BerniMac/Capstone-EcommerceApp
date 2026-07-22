/* Invoice.java
   Invoice POJO class
   Author: Mogamad Jawaad Allie - 230472125
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import jakarta.persistence.*;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final String invoiceId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private final String orderId;
    private final String invoiceDate;
    private final double totalAmount;
    private final double taxAmount;
    private final String invoiceStatus;

    private Invoice(Builder builder) {
        this.invoiceId = builder.invoiceId;
        this.orderId = builder.orderId;
        this.invoiceDate = builder.invoiceDate;
        this.totalAmount = builder.totalAmount;
        this.taxAmount = builder.taxAmount;
        this.invoiceStatus = builder.invoiceStatus;
    }

    // Getters
    public String getInvoiceId() {
        return invoiceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    // Builder Class
    public static class Builder {
        private String invoiceId;
        private String orderId;
        private String invoiceDate;
        private double totalAmount;
        private double taxAmount;
        private String invoiceStatus;

        public Builder setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
            return this;
        }

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setInvoiceDate(String invoiceDate) {
            this.invoiceDate = invoiceDate;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setTaxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
            return this;
        }

        public Builder setInvoiceStatus(String invoiceStatus) {
            this.invoiceStatus = invoiceStatus;
            return this;
        }

        public Builder copy(Invoice invoice) {
            this.invoiceId = invoice.invoiceId;
            this.orderId = invoice.orderId;
            this.invoiceDate = invoice.invoiceDate;
            this.totalAmount = invoice.totalAmount;
            this.taxAmount = invoice.taxAmount;
            this.invoiceStatus = invoice.invoiceStatus;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", totalAmount=" + totalAmount +
                ", taxAmount=" + taxAmount +
                ", invoiceStatus='" + invoiceStatus + '\'' +
                '}';
    }
}