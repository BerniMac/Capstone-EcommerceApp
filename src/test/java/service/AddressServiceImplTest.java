package service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.factory.AddressFactory;
import za.ca.cput.commerce.repository.AddressRepository;
import za.ca.cput.commerce.service.impl.AddressServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private Customer customer;

    @BeforeEach
    void setUp() {

        customer = new Customer.Builder()
                .setCustomerId("C001")
                .setName("John")
                .setEmail("j@Doe.com")
                .build();

        address = AddressFactory.createAddress(
                customer,
                "123 Main Street",
                "Cape Town",
                "Western Cape",
                "8001",
                "South Africa",
                "Home"
        );
    }

    @Test
    void createAddress() {

        when(addressRepository.save(address)).thenReturn(address);

        Address saved = addressService.createAddress(address);

        assertNotNull(saved);
        assertEquals("Cape Town", saved.getCity());

        verify(addressRepository).save(address);
    }

    @Test
    void getAddressById() {

        when(addressRepository.findById("A001"))
                .thenReturn(Optional.of(address));

        Address found = addressService.getAddressById("A001");

        assertNotNull(found);

        verify(addressRepository).findById("A001");
    }

    @Test
    void getAllAddresses() {

        when(addressRepository.findAll())
                .thenReturn(List.of(address));

        List<Address> addresses = addressService.getAllAddresses();

        assertEquals(1, addresses.size());

        verify(addressRepository).findAll();
    }

    @Test
    void updateAddress() {

        Address updated = new Address.Builder()
                .copy(address)
                .setCity("Johannesburg")
                .build();

        when(addressRepository.findById("A001"))
                .thenReturn(Optional.of(address));

        when(addressRepository.save(any(Address.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Address result = addressService.updateAddress("A001", updated);

        assertNotNull(result);
        assertEquals("Johannesburg", result.getCity());

        verify(addressRepository).findById("A001");
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void deleteAddress() {

        doNothing().when(addressRepository).deleteById("A001");

        addressService.deleteAddress("A001");

        verify(addressRepository).deleteById("A001");
    }

    @Test
    void getAddressesByCustomerId() {

        when(addressRepository.findByCustomerCustomerId("C001"))
                .thenReturn(List.of(address));

        List<Address> addresses =
                addressService.getAddressesByCustomerId("C001");

        assertEquals(1, addresses.size());

        verify(addressRepository)
                .findByCustomerCustomerId("C001");
    }
}
