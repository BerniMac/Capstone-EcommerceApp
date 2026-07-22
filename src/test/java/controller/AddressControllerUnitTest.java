package controller;
/*
   Author: 222709006 Qhama dyushu
   Date: 19 July 2026
*/
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
import za.ca.cput.commerce.controller.AddressController;
import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.service.AddressService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
class AddressControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AddressService addressService;

    private Address sampleAddress() {
        return new Address.Builder()
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
    void givenAddresses_whenGetAllAddresses_thenReturnJsonArray() throws Exception {
        given(addressService.findAll()).willReturn(List.of(sampleAddress()));

        mockMvc.perform(get("/api/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].addressId", org.hamcrest.Matchers.is("addr-1")));
    }

    @Test
    void givenValidId_whenGetAddressById_thenReturnAddress() throws Exception {
        given(addressService.findById("addr-1")).willReturn(sampleAddress());

        mockMvc.perform(get("/api/addresses/{id}", "addr-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", org.hamcrest.Matchers.is("Cape Town")));
    }

    @Test
    void givenInvalidId_whenGetAddressById_thenReturn404() throws Exception {
        given(addressService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Address not found with id: bad-id"));

        mockMvc.perform(get("/api/addresses/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidAddress_whenCreateAddress_thenReturn201() throws Exception {
        Address address = sampleAddress();
        given(addressService.save(any(Address.class))).willReturn(address);

        mockMvc.perform(post("/api/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.addressId", org.hamcrest.Matchers.is("addr-1")));
    }

    @Test
    void givenValidIdAndAddress_whenUpdateAddress_thenReturnUpdatedAddress() throws Exception {
        Address updated = sampleAddress();
        given(addressService.update(eq("addr-1"), any(Address.class))).willReturn(updated);

        mockMvc.perform(put("/api/addresses/{id}", "addr-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId", org.hamcrest.Matchers.is("addr-1")));
    }

    @Test
    void givenValidId_whenDeleteAddress_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/addresses/{id}", "addr-1"))
                .andExpect(status().isNoContent());
    }
}