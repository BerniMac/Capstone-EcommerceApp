/* Address.java
   Address POJO class
   Author: 222709006 Qhama dyushu
   Date: 21 June 2026
*/
package za.ca.cput.commerce.domain;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final String addressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private final String customerId;
    private final String streetAddress;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String country;
    private final String addressType;

    private Address(Builder builder) {
        this.addressId = builder.addressId;
        this.customerId = builder.customerId;
        this.streetAddress = builder.streetAddress;
        this.city = builder.city;
        this.state = builder.state;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
        this.addressType = builder.addressType;
    }

    // Getters
    public String getAddressId() {
        return addressId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getAddressType() {
        return addressType;
    }

    // Builder Class
    public static class Builder {
        private String addressId;
        private String customerId;
        private String streetAddress;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private String addressType;

        public Builder setAddressId(String addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setAddressType(String addressType) {
            this.addressType = addressType;
            return this;
        }

        public Builder copy(Address address) {
            this.addressId = address.addressId;
            this.customerId = address.customerId;
            this.streetAddress = address.streetAddress;
            this.city = address.city;
            this.state = address.state;
            this.postalCode = address.postalCode;
            this.country = address.country;
            this.addressType = address.addressType;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId='" + addressId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", addressType='" + addressType + '\'' +
                '}';
    }
}