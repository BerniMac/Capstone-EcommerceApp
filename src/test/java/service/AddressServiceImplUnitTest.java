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
import za.ca.cput.commerce.repository.AddressRepository;
import za.ca.cput.commerce.service.impl.AddressServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplUnitTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address existingAddress;

    @BeforeEach
    void setUp() {
        existingAddress = new Address.Builder()
                .setAddressId("addr-1")
                .setCustomerId("cust-1")
                .setStreetAddress("12 Main Road")
                .setCity("Cape Town")
                .setState("Western Cape")
                .setPostalCode("8001")
                .setCountry("South Africa")
                .setAddressType("HOME")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedAddress() {
        given(addressRepository.save(existingAddress)).willReturn(existingAddress);

        Address saved = addressService.save(existingAddress);

        assertThat(saved).isEqualTo(existingAddress);
        verify(addressRepository, times(1)).save(existingAddress);
    }

    @Test
    void whenFindAll_thenReturnListOfAddresses() {
        given(addressRepository.findAll()).willReturn(List.of(existingAddress));

        List<Address> addresses = addressService.findAll();

        assertThat(addresses).hasSize(1).contains(existingAddress);
    }

    @Test
    void whenValidId_thenAddressShouldBeFound() {
        given(addressRepository.findById("addr-1")).willReturn(Optional.of(existingAddress));

        Address found = addressService.findById("addr-1");

        assertThat(found.getAddressId()).isEqualTo("addr-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(addressRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.findById("bad-id"));
    }

    @Test
    void whenUpdate_thenRebuiltAddressIsSavedWithNewFields() {
        Address updateRequest = new Address.Builder()
                .setStreetAddress("99 New Street")
                .setCity("Stellenbosch")
                .setState("Western Cape")
                .setPostalCode("7600")
                .setCountry("South Africa")
                .setAddressType("WORK")
                .build();

        given(addressRepository.findById("addr-1")).willReturn(Optional.of(existingAddress));
        given(addressRepository.save(org.mockito.ArgumentMatchers.any(Address.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Address result = addressService.update("addr-1", updateRequest);

        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).save(captor.capture());
        Address saved = captor.getValue();

        // id + customerId should be preserved from the existing record
        assertThat(saved.getAddressId()).isEqualTo("addr-1");
        assertThat(saved.getCustomerId()).isEqualTo("cust-1");
        // mutable fields should reflect the update request
        assertThat(saved.getStreetAddress()).isEqualTo("99 New Street");
        assertThat(saved.getCity()).isEqualTo("Stellenbosch");
        assertThat(saved.getPostalCode()).isEqualTo("7600");
        assertThat(saved.getAddressType()).isEqualTo("WORK");
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(addressRepository.findById("addr-1")).willReturn(Optional.of(existingAddress));

        addressService.deleteById("addr-1");

        verify(addressRepository, times(1)).delete(existingAddress);
    }
}
