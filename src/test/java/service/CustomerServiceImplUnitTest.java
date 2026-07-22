package service;

/*
Author: 222709006 Qhama dyushu
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.repository.CustomerRepository;
import za.ca.cput.commerce.service.impl.CustomerServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer existingCustomer;

    @BeforeEach
    void setUp() {
        existingCustomer = new Customer.Builder()
                .setCustomerId("cust-1")
                .setName("Anesu Moyo")
                .setEmail("anesu@example.com")
                .setPhone("0821234567")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedCustomer() {
        given(customerRepository.save(existingCustomer)).willReturn(existingCustomer);

        Customer saved = customerService.save(existingCustomer);

        assertThat(saved).isEqualTo(existingCustomer);
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void whenFindAll_thenReturnListOfCustomers() {
        given(customerRepository.findAll()).willReturn(List.of(existingCustomer));

        List<Customer> customers = customerService.findAll();

        assertThat(customers).hasSize(1).contains(existingCustomer);
    }

    @Test
    void whenValidId_thenCustomerShouldBeFound() {
        given(customerRepository.findById("cust-1")).willReturn(Optional.of(existingCustomer));

        Customer found = customerService.findById("cust-1");

        assertThat(found.getCustomerId()).isEqualTo("cust-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(customerRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.findById("bad-id"));
    }

    @Test
    void whenUpdate_thenRebuiltCustomerIsSavedWithNewFields() {
        Customer updateRequest = new Customer.Builder()
                .setName("Anesu M. Chowuraya")
                .setEmail("new.email@example.com")
                .setPhone("0827654321")
                .build();

        given(customerRepository.findById("cust-1")).willReturn(Optional.of(existingCustomer));
        given(customerRepository.save(any(Customer.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Customer result = customerService.update("cust-1", updateRequest);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(captor.capture());
        Customer saved = captor.getValue();

        assertThat(saved.getCustomerId()).isEqualTo("cust-1");
        assertThat(saved.getName()).isEqualTo("Anesu M. Chowuraya");
        assertThat(saved.getEmail()).isEqualTo("new.email@example.com");
        assertThat(saved.getPhone()).isEqualTo("0827654321");
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(customerRepository.findById("cust-1")).willReturn(Optional.of(existingCustomer));

        customerService.deleteById("cust-1");

        verify(customerRepository, times(1)).delete(existingCustomer);
    }
}
