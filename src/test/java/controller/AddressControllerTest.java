package controller;
/*
import domain.Address;
import factory.AddressFactory;
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
import service.AddressService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AddressController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {AddressController.class, AddressControllerTest.MockServiceConfig.class})
public class AddressControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public AddressService addressService() {
            return org.mockito.Mockito.mock(AddressService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressService addressService;

    @Test
    public void testCreate() throws Exception {
        Address entity = AddressFactory.createAddress("ADDR-101","CUST-202","123 Main St","Cape Town","Western Cape","8001","South Africa","Shipping");
        when(addressService.create(any(Address.class))).thenReturn(entity);
        String json = "{\"addressId\":\"ADDR-101\",\"customerId\":\"CUST-202\",\"streetAddress\":\"123 Main St\",\"city\":\"Cape Town\",\"state\":\"Western Cape\",\"postalCode\":\"8001\",\"country\":\"South Africa\",\"addressType\":\"Shipping\"}";
        mockMvc.perform(post("/address/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(entity.getAddressId()));
    }

    @Test
    public void testRead() throws Exception {
        Address entity = AddressFactory.createAddress("ADDR-101","CUST-202","123 Main St","Cape Town","Western Cape","8001","South Africa","Shipping");
        when(addressService.read(entity.getAddressId())).thenReturn(entity);
        mockMvc.perform(get("/address/read/" + entity.getAddressId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(entity.getAddressId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Address entity = AddressFactory.createAddress("ADDR-101","CUST-202","123 Main St","Cape Town","Western Cape","8001","South Africa","Shipping");
        when(addressService.update(any(Address.class))).thenReturn(entity);
        String json = "{\"addressId\":\"ADDR-101\",\"customerId\":\"CUST-202\",\"streetAddress\":\"123 Main St\",\"city\":\"Cape Town\",\"state\":\"Western Cape\",\"postalCode\":\"8001\",\"country\":\"South Africa\",\"addressType\":\"Shipping\"}";
        mockMvc.perform(post("/address/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(entity.getAddressId()));
    }

    @Test
    public void testDelete() throws Exception {
        Address entity = AddressFactory.createAddress("ADDR-101","CUST-202","123 Main St","Cape Town","Western Cape","8001","South Africa","Shipping");
        when(addressService.delete(entity.getAddressId())).thenReturn(true);
        mockMvc.perform(delete("/address/delete/" + entity.getAddressId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Address entity = AddressFactory.createAddress("ADDR-101","CUST-202","123 Main St","Cape Town","Western Cape","8001","South Africa","Shipping");
        when(addressService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/address/getall"))
                .andExpect(status().isOk());
    }
}

 */
