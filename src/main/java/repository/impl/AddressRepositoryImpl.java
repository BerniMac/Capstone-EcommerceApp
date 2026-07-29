/* AddressRepositoryImpl.java
   AddressRepositoryImpl class
   Author: 222709006 Qhama dyushu
   Date: 17 July 2026
*/
package repository.impl;

import domain.Address;
import repository.AddressRepository;
import java.util.*;

public class AddressRepositoryImpl implements AddressRepository {
    private static AddressRepositoryImpl repository = null;
    private final Map<String, Address> addressDB = new HashMap<>();

    public AddressRepositoryImpl() {
    }

    public static AddressRepositoryImpl getRepository() {
        if (repository == null) {
            repository = new AddressRepositoryImpl();
        }
        return repository;
    }

    @Override
    public Address create(Address address) {
        addressDB.put(address.getAddressId(), address);
        return address;
    }

    @Override
    public Address read(String addressId) {
        return addressDB.get(addressId);
    }

    @Override
    public Address update(Address address) {
        if (addressDB.containsKey(address.getAddressId())) {
            addressDB.put(address.getAddressId(), address);
            return address;
        }
        return null;
    }

    @Override
    public boolean delete(String addressId) {
        return addressDB.remove(addressId) != null;
    }

    @Override
    public List<Address> findAll() {
        return new ArrayList<>(addressDB.values());
    }
}
