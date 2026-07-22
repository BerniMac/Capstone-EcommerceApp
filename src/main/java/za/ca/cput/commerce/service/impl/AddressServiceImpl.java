package za.ca.cput.commerce.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.repository.AddressRepository;
import za.ca.cput.commerce.service.AddressService;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(String id) {
        return addressRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Address" + id));
    }

    @Override
    public Address update(String id, Address address) {
        Address existing = findById(id);

        Address updated = new Address.Builder()
                .copy(existing)
                .setStreetAddress(address.getStreetAddress())
                .setCity(address.getCity())
                .setState(address.getState())
                .setPostalCode(address.getPostalCode())
                .setCountry(address.getCountry())
                .setAddressType(address.getAddressType())
                .build();
        return addressRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        addressRepository.delete(findById(id));
    }
}
