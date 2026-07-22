package za.ca.cput.commerce.service;

/*
Author: 222709006 Qhama dyushu
19/07/2026
 */

import za.ca.cput.commerce.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);

    List<Customer> findAll();

    Customer findById(String id);

    Customer update(String id, Customer customer);

    void deleteById(String id);
}
