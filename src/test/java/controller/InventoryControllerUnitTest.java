package controller;
/*
   Author: Plamedie 230082629
   Date: 19 july 2026
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
import za.ca.cput.commerce.controller.InventoryController;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.service.InventoryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InventoryController.class)
class InventoryControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventoryService inventoryService;

    private Inventory sampleInventory() {
        return new Inventory.Builder()
                .setInventoryId("inv-1")
                .setProductId("prod-1")
                .setStockQuantity(50)
                .setWarehouseLocation("Cape Town DC")
                .setLastUpdated("2026-01-01T00:00:00")
                .build();
    }

    @Test
    void givenInventoryRecords_whenGetAllInventory_thenReturnJsonArray() throws Exception {
        given(inventoryService.findAll()).willReturn(List.of(sampleInventory()));

        mockMvc.perform(get("/api/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].inventoryId", org.hamcrest.Matchers.is("inv-1")));
    }

    @Test
    void givenValidId_whenGetInventoryById_thenReturnInventory() throws Exception {
        given(inventoryService.findById("inv-1")).willReturn(sampleInventory());

        mockMvc.perform(get("/api/inventory/{id}", "inv-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockQuantity", org.hamcrest.Matchers.is(50)));
    }

    @Test
    void givenInvalidId_whenGetInventoryById_thenReturn404() throws Exception {
        given(inventoryService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Inventory not found with id: bad-id"));

        mockMvc.perform(get("/api/inventory/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidInventory_whenCreateInventory_thenReturn201() throws Exception {
        Inventory inventory = sampleInventory();
        given(inventoryService.save(any(Inventory.class))).willReturn(inventory);

        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.inventoryId", org.hamcrest.Matchers.is("inv-1")));
    }

    @Test
    void givenValidIdAndInventory_whenUpdateInventory_thenReturnUpdatedInventory() throws Exception {
        Inventory updated = sampleInventory();
        given(inventoryService.update(eq("inv-1"), any(Inventory.class))).willReturn(updated);

        mockMvc.perform(put("/api/inventory/{id}", "inv-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId", org.hamcrest.Matchers.is("inv-1")));
    }

    @Test
    void givenValidId_whenDeleteInventory_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/inventory/{id}", "inv-1"))
                .andExpect(status().isNoContent());
    }
}