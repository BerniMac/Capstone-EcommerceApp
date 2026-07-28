/* CustomerRepositoryImpl.java
   Author: 222709006 Qhama dyushu
   Date: 17 July 2026
*/
package repository.impl;

import domain.Customer;
import repository.CustomerRepository;

import java.util.*;

public class CustomerRepositoryImpl implements CustomerRepository {

    private static CustomerRepositoryImpl repository = null;
    private Map<String, Customer> customerDB;

    CustomerRepositoryImpl() {
        customerDB = new HashMap<>();
    }

    public static CustomerRepositoryImpl getRepository() {
        if (repository == null) {
            repository = new CustomerRepositoryImpl();
        }
        return repository;
    }

    @Override
    public Customer create(Customer customer) {
        customerDB.put(customer.getCustomerId(), customer);
        return customer;
    }

    @Override
    public Customer read(String customerId) {
        return customerDB.get(customerId);
    }

    @Override
    public Customer update(Customer customer) {
        if (customerDB.containsKey(customer.getCustomerId())) {
            customerDB.put(customer.getCustomerId(), customer);
            return customer;
        }
        return null;
    }

    @Override
    public boolean delete(String customerId) {
        return customerDB.remove(customerId) != null;
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customerDB.values());
    }
}
