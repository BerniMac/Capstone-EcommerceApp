package za.ac.cput.ecommerce.controller;
/*
   Author: Plamedie 230082629
   Date: 19 july 2026
*/
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockitoBean;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.InventoryController;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.service.InventoryService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product createProduct() {
        return new Product.Builder()
                .setProductId("P001")
                .setProductName("Laptop")
                .build();
    }

    private Inventory buildInventory() {
        return new Inventory.Builder()
                .setInventoryId("I001")
                .setProduct(createProduct())
                .setStockQuantity(50)
                .setWarehouseLocation("Warehouse A")
                .setLastUpdated(LocalDateTime.now())
                .build();
    }

    @Test
    void createInventory() throws Exception {

        Inventory inventory = buildInventory();

        when(inventoryService.createInventory(any(Inventory.class)))
                .thenReturn(inventory);

        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.inventoryId").value("I001"))
                .andExpect(jsonPath("$.stockQuantity").value(50));

        verify(inventoryService).createInventory(any(Inventory.class));
    }

    @Test
    void getInventoryById() throws Exception {

        Inventory inventory = buildInventory();

        when(inventoryService.getInventoryById("I001"))
                .thenReturn(inventory);

        mockMvc.perform(get("/api/inventory/I001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value("I001"))
                .andExpect(jsonPath("$.warehouseLocation")
                        .value("Warehouse A"));

        verify(inventoryService).getInventoryById("I001");
    }

    @Test
    void getAllInventory() throws Exception {

        when(inventoryService.getAllInventory())
                .thenReturn(List.of(buildInventory()));

        mockMvc.perform(get("/api/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].inventoryId")
                        .value("I001"));

        verify(inventoryService).getAllInventory();
    }

    @Test
    void updateInventory() throws Exception {

        Inventory inventory = buildInventory();

        when(inventoryService.updateInventory(eq("I001"), any(Inventory.class)))
                .thenReturn(inventory);

        mockMvc.perform(put("/api/inventory/I001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockQuantity").value(50));

        verify(inventoryService).updateInventory(eq("I001"), any(Inventory.class));
    }

    @Test
    void deleteInventory() throws Exception {

        doNothing().when(inventoryService).deleteInventory("I001");

        mockMvc.perform(delete("/api/inventory/I001"))
                .andExpect(status().isNoContent());

        verify(inventoryService).deleteInventory("I001");
    }

    @Test
    void getInventoryByProduct() throws Exception {

        Inventory inventory = buildInventory();

        when(inventoryService.getInventoryByProductId(anyString()))
                .thenReturn(inventory);

        mockMvc.perform(get("/api/inventory/product/P001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value("I001"))
                .andExpect(jsonPath("$.stockQuantity").value(50));

        verify(inventoryService).getInventoryByProductId("P001");
    }
}