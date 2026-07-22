package controller;
/*
 * ShipmentFactoryTest.java
 * Author: Tlangelani Chauke
 * Date:19 july 2026
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.ShipmentController;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.service.ShipmentService;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShipmentController.class)
class ShipmentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ShipmentService shipmentService;

    private Shipment sampleShipment(String status) {
        return new Shipment.Builder()
                .setShipmentId("ship-1")
                .setAddress("12 Main Road, Cape Town")
                .setShipmentDate(new Date(0))
                .setStatus(status)
                .build();
    }

    @Test
    void givenShipments_whenGetAllShipments_thenReturnJsonArray() throws Exception {
        given(shipmentService.findAll()).willReturn(List.of(sampleShipment("PROCESSING")));

        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].shipmentId", org.hamcrest.Matchers.is("ship-1")));
    }

    @Test
    void givenValidId_whenGetShipmentById_thenReturnShipment() throws Exception {
        given(shipmentService.findById("ship-1")).willReturn(sampleShipment("PROCESSING"));

        mockMvc.perform(get("/api/shipments/{id}", "ship-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", org.hamcrest.Matchers.is("PROCESSING")));
    }

    @Test
    void givenInvalidId_whenGetShipmentById_thenReturn404() throws Exception {
        given(shipmentService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Shipment not found with id: bad-id"));

        mockMvc.perform(get("/api/shipments/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidShipment_whenCreateShipment_thenReturn201() throws Exception {
        Shipment shipment = sampleShipment("PROCESSING");
        given(shipmentService.save(any(Shipment.class))).willReturn(shipment);

        mockMvc.perform(post("/api/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shipment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shipmentId", org.hamcrest.Matchers.is("ship-1")));
    }

    @Test
    void givenValidIdAndStatus_whenUpdateShipmentStatus_thenReturnUpdatedShipment() throws Exception {
        given(shipmentService.updateStatus(eq("ship-1"), eq("DELIVERED")))
                .willReturn(sampleShipment("DELIVERED"));

        mockMvc.perform(patch("/api/shipments/{id}/status", "ship-1")
                        .param("status", "DELIVERED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", org.hamcrest.Matchers.is("DELIVERED")));
    }

    @Test
    void givenValidId_whenDeleteShipment_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/shipments/{id}", "ship-1"))
                .andExpect(status().isNoContent());
    }
}
