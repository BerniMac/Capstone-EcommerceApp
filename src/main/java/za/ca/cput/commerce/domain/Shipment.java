/*
 * Shipment.java
 * Shipment model class
 * Author: Tlangelani Chauke
 * Date:22march 2026
 */
package za.ca.cput.commerce.domain;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String shipmentId;
    private String address;
    private Date shipmentDate;
    private Date deliveryDate;
    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    // Private constructor
    private Shipment(Builder builder) {
        this.shipmentId = builder.shipmentId;
        this.address = builder.address;
        this.shipmentDate = builder.shipmentDate;
        this.deliveryDate = builder.deliveryDate;
        this.status = builder.status;
    }

    // Getters
    public String getShipmentId() {
        return shipmentId;
    }

    public String getAddress() {
        return address;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    // Builder Class
    public static class Builder {
        private String shipmentId;
        private String address;
        private Date shipmentDate;
        private Date deliveryDate;
        private String status;

        public Builder setShipmentId(String shipmentId) {
            this.shipmentId = shipmentId;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setShipmentDate(Date shipmentDate) {
            this.shipmentDate = shipmentDate;
            return this;
        }

        public Builder setDeliveryDate(Date deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder copy(Shipment shipment) {
            this.shipmentId = shipment.shipmentId;
            this.address = shipment.address;
            this.shipmentDate = shipment.shipmentDate;
            this.deliveryDate = shipment.deliveryDate;
            this.status = shipment.status;
            return this;
        }

        public Shipment build() {
            return new Shipment(this);
        }
    }
}
