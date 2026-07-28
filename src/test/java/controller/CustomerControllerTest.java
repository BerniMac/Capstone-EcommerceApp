/* CustomerControllerTest.java
   Author: 222709006 Qhama dyushu
   Date: 17 July 2026
*/
package controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import service.CustomerService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {CustomerController.class, CustomerControllerTest.MockServiceConfig.class})
public class CustomerControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public CustomerService customerService() {
            return org.mockito.Mockito.mock(CustomerService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreate() throws Exception {
        Customer entity = CustomerFactory.createCustomer("CUST-101","John Doe","john@example.com","0123456789");
        when(customerService.create(any(Customer.class))).thenReturn(entity);
        String json = "{\"customerId\":\"CUST-101\",\"name\":\"John Doe\",\"email\":\"john@example.com\",\"phone\":\"0123456789\"}";
        mockMvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(entity.getCustomerId()));
    }

    @Test
    public void testRead() throws Exception {
        Customer entity = CustomerFactory.createCustomer("CUST-101","John Doe","john@example.com","0123456789");
        when(customerService.read(entity.getCustomerId())).thenReturn(entity);
        mockMvc.perform(get("/customer/read/" + entity.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(entity.getCustomerId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Customer entity = CustomerFactory.createCustomer("CUST-101","John Doe","john@example.com","0123456789");
        when(customerService.update(any(Customer.class))).thenReturn(entity);
        String json = "{\"customerId\":\"CUST-101\",\"name\":\"John Doe\",\"email\":\"john@example.com\",\"phone\":\"0123456789\"}";
        mockMvc.perform(post("/customer/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(entity.getCustomerId()));
    }

    @Test
    public void testDelete() throws Exception {
        Customer entity = CustomerFactory.createCustomer("CUST-101","John Doe","john@example.com","0123456789");
        when(customerService.delete(entity.getCustomerId())).thenReturn(true);
        mockMvc.perform(delete("/customer/delete/" + entity.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Customer entity = CustomerFactory.createCustomer("CUST-101","John Doe","john@example.com","0123456789");
        when(customerService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/customer/getall"))
                .andExpect(status().isOk());
    }
}
