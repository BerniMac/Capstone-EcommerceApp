package za.ca.cput.commerce.service;

import za.ca.cput.commerce.domain.Address;

import java.util.List;

public interface AddressService {
    Address save(Address address);
    List<Address> findAll();
    Address findById(String id);
    Address update(String id, Address address);
    void deleteById(String id);
}
