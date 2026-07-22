/* 
  Customer____.java
  Author: 222709006 Qhama dyushu
  Date: 22/03/2026
    */
package za.ca.cput.commerce.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String customerId;
    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


    // Private constructor (Builder pattern)
    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.orders = new ArrayList<>();
        //this.cards = new ArrayList<>();
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    //public List<Card> getCards() {
    //  return cards;
    // }



    // Add Order
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    // Remove Order
    public void removeOrder(Order order) {
        this.orders.remove(order);
    }

    // Add Card
    // public void addCard(Card card) {
    //  this.cards.add(card);
    //}

    // Remove Card
    // public void removeCard(Card card) {
    //  this.cards.remove(card);
    //}

    // Find Order by ID
    public Order findOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    // Find Card by ID
//    public Card findCardById(String cardId) {
//        for (Card card : cards) {
//            if (card.getCardId().equals(cardId)) {
//                return card;
//            }
//        }
//        return null;
//    }

    // toString()
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    //  Builder Pattern
    public static class Builder {
        private String customerId;
        private String name;
        private String email;
        private String phone;

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder copy(Customer customer) {
            this.customerId = customer.customerId;
            this.name = customer.name;
            this.email = customer.email;
            this.phone = customer.phone;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
