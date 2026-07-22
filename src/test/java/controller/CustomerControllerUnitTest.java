package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.CustomerController;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.service.CustomerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService customerService;

    private Customer sampleCustomer() {
        return new Customer.Builder()
                .setCustomerId("cust-1")
                .setName("Anesu Moyo")
                .setEmail("anesu@example.com")
                .setPhone("0821234567")
                .build();
    }

    @Test
    void givenCustomers_whenGetAllCustomers_thenReturnJsonArray() throws Exception {
        given(customerService.findAll()).willReturn(List.of(sampleCustomer()));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].customerId", org.hamcrest.Matchers.is("cust-1")));
    }

    @Test
    void givenValidId_whenGetCustomerById_thenReturnCustomer() throws Exception {
        given(customerService.findById("cust-1")).willReturn(sampleCustomer());

        mockMvc.perform(get("/api/customers/{id}", "cust-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", org.hamcrest.Matchers.is("anesu@example.com")));
    }

    @Test
    void givenInvalidId_whenGetCustomerById_thenReturn404() throws Exception {
        given(customerService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Customer not found with id: bad-id"));

        mockMvc.perform(get("/api/customers/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidCustomer_whenCreateCustomer_thenReturn201() throws Exception {
        Customer customer = sampleCustomer();
        given(customerService.save(any(Customer.class))).willReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId", org.hamcrest.Matchers.is("cust-1")));
    }

    @Test
    void givenValidIdAndCustomer_whenUpdateCustomer_thenReturnUpdatedCustomer() throws Exception {
        Customer updated = sampleCustomer();
        given(customerService.update(eq("cust-1"), any(Customer.class))).willReturn(updated);

        mockMvc.perform(put("/api/customers/{id}", "cust-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", org.hamcrest.Matchers.is("cust-1")));
    }

    @Test
    void givenValidId_whenDeleteCustomer_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/customers/{id}", "cust-1"))
                .andExpect(status().isNoContent());
    }
}
