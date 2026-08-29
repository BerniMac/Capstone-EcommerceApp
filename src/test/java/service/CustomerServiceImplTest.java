package service;

/*
Author: 222709006 Qhama dyushu
12/07/2026
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer.Builder()
                .setCustomerId("C001")
                .setName("John Doe")
                .setEmail("john@example.com")
                .setPhone("0123456789")
                .build();
    }

    @Test
    void create() {

        when(repository.save(customer)).thenReturn(customer);

        Customer created = service.create(customer);

        assertNotNull(created);
        assertEquals(customer.getCustomerId(), created.getCustomerId());

        verify(repository).save(customer);
    }

    @Test
    void read() {

        when(repository.findById("C001"))
                .thenReturn(Optional.of(customer));

        Customer found = service.read("C001");

        assertNotNull(found);
        assertEquals("John Doe", found.getName());

        verify(repository).findById("C001");
    }

    @Test
    void readCustomerNotFound() {

        when(repository.findById("C001"))
                .thenReturn(Optional.empty());

        Customer found = service.read("C001");

        assertNull(found);

        verify(repository).findById("C001");
    }

    @Test
    void update() {

        Customer updatedInfo = new Customer.Builder()
                .setName("Jane Doe")
                .setEmail("jane@example.com")
                .setPhone("0987654321")
                .build();

        when(repository.findById("C001"))
                .thenReturn(Optional.of(customer));

        when(repository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Customer updated = service.update("C001", updatedInfo);

        assertNotNull(updated);
        assertEquals("C001", updated.getCustomerId());
        assertEquals("Jane Doe", updated.getName());
        assertEquals("jane@example.com", updated.getEmail());
        assertEquals("0987654321", updated.getPhone());

        verify(repository).save(any(Customer.class));
    }

    @Test
    void updateCustomerNotFound() {

        when(repository.findById("C001"))
                .thenReturn(Optional.empty());

        Customer updated = service.update("C001", customer);

        assertNull(updated);

        verify(repository, never()).save(any(Customer.class));
    }

    @Test
    void delete() {

        when(repository.existsById("C001"))
                .thenReturn(true);

        doNothing().when(repository).deleteById("C001");

        boolean deleted = service.delete("C001");

        assertTrue(deleted);

        verify(repository).deleteById("C001");
    }

    @Test
    void deleteCustomerNotFound() {

        when(repository.existsById("C001"))
                .thenReturn(false);

        boolean deleted = service.delete("C001");

        assertFalse(deleted);

        verify(repository, never()).deleteById(anyString());
    }

    @Test
    void getAll() {

        List<Customer> customers = List.of(customer);

        when(repository.findAll()).thenReturn(customers);

        List<Customer> result = service.getAll();

        assertEquals(1, result.size());

        verify(repository).findAll();
    }

    @Test
    void findByEmail() {

        when(repository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        Optional<Customer> found =
                service.findByEmail("john@example.com");

        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getName());

        verify(repository).findByEmail("john@example.com");
    }

    @Test
    void searchByName() {

        List<Customer> customers = List.of(customer);

        when(repository.findByNameContainingIgnoreCase("John"))
                .thenReturn(customers);

        List<Customer> result = service.searchByName("John");

        assertEquals(1, result.size());

        verify(repository)
                .findByNameContainingIgnoreCase("John");
    }
}