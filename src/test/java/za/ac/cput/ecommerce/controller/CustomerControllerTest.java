package za.ac.cput.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.CustomerController;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.service.CustomerService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService service;

    private Customer buildCustomer() {
        return new Customer.Builder()
                .setCustomerId("C001")
                .setName("John Doe")
                .setEmail("john@example.com")
                .setPhone("0123456789")
                .build();
    }

    @Test
    void createCustomer() throws Exception {

        Customer customer = buildCustomer();

        when(service.create(any(Customer.class)))
                .thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value("C001"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getCustomer() throws Exception {

        Customer customer = buildCustomer();

        when(service.read("C001"))
                .thenReturn(customer);

        mockMvc.perform(get("/api/customers/C001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("C001"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getCustomerNotFound() throws Exception {

        when(service.read("C001"))
                .thenReturn(null);

        mockMvc.perform(get("/api/customers/C001"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCustomer() throws Exception {

        Customer customer = buildCustomer();

        when(service.update(any(String.class), any(Customer.class)))
                .thenReturn(customer);

        mockMvc.perform(put("/api/customers/C001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void deleteCustomer() throws Exception {

        doReturn(true).when(service).delete("C001");

        mockMvc.perform(delete("/api/customers/C001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomerNotFound() throws Exception {

        doReturn(false).when(service).delete("C001");

        mockMvc.perform(delete("/api/customers/C001"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCustomers() throws Exception {

        when(service.getAll())
                .thenReturn(List.of(buildCustomer()));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getCustomerByEmail() throws Exception {

        Customer customer = buildCustomer();

        when(service.findByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("C001"));
    }

    @Test
    void searchCustomers() throws Exception {

        when(service.searchByName("John"))
                .thenReturn(List.of(buildCustomer()));

        mockMvc.perform(get("/api/customers/search")
                        .param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
