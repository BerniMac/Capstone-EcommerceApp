/* CustomerServiceImpl.java
   Author: 222709006 Qhama dyushu
   Date: 17 July 2026
*/
package service.impl;

import domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CustomerRepository;
import service.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer create(Customer entity) {
        return repository.create(entity);
    }

    @Override
    public Customer read(String id) {
        return repository.read(id);
    }

    @Override
    public Customer update(Customer entity) {
        return repository.update(entity);
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }
}
