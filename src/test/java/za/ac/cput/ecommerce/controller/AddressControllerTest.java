package za.ac.cput.ecommerce.controller;
/*
   Author: 222709006 Qhama dyushu
   Date: 19 July 2026
*/
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.AddressController;
import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.factory.AddressFactory;
import za.ca.cput.commerce.service.AddressService;
import za.ca.cput.commerce.service.CustomerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private Address address;

    @BeforeEach
    void setUp() {

        Customer customer = new Customer.Builder()
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
    void createAddress() throws Exception {

        when(addressService.createAddress(any(Address.class)))
                .thenReturn(address);

        mockMvc.perform(post("/api/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("Cape Town"));
    }

    @Test
    void getAddressById() throws Exception {

        when(addressService.getAddressById("A001"))
                .thenReturn(address);

        mockMvc.perform(get("/api/addresses/A001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Cape Town"));
    }

    @Test
    void getAllAddresses() throws Exception {

        when(addressService.getAllAddresses())
                .thenReturn(List.of(address));

        mockMvc.perform(get("/api/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void updateAddress() throws Exception {

        when(addressService.updateAddress(any(String.class), any(Address.class)))
                .thenReturn(address);

        mockMvc.perform(put("/api/addresses/A001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Cape Town"));
    }

    @Test
    void deleteAddress() throws Exception {

        when(addressService.getAddressById("A001"))
                .thenReturn(address);

        doNothing().when(addressService).deleteAddress("A001");

        mockMvc.perform(delete("/api/addresses/A001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAddressesByCustomerId() throws Exception {

        when(addressService.getAddressesByCustomerId("C001"))
                .thenReturn(List.of(address));

        mockMvc.perform(get("/api/addresses/customer/C001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}