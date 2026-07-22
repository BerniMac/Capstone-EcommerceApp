package za.ca.cput.commerce.service.impl;

/*
Author: 222709006 Qhama dyushu
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.repository.CustomerRepository;
import za.ca.cput.commerce.service.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer"+ id));
    }

    @Override
    public Customer update(String id, Customer customer) {
        Customer existing = findById(id);

        Customer updated = new Customer.Builder()
                .copy(existing)
                .setName(customer.getName())
                .setEmail(customer.getEmail())
                .setPhone(customer.getPhone())
                .build();
        return customerRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        Customer existing = findById(id);
        customerRepository.delete(existing);
    }
}

