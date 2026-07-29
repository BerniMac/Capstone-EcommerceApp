package controller;
/*
import domain.Inventory;
import factory.InventoryFactory;
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
import service.InventoryService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InventoryController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {InventoryController.class, InventoryControllerTest.MockServiceConfig.class})
public class InventoryControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public InventoryService inventoryService() {
            return org.mockito.Mockito.mock(InventoryService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryService inventoryService;

    @Test
    public void testCreate() throws Exception {
        Inventory entity = InventoryFactory.createInventory("INV-101","PROD-101",100,"Aisle A","2026-07-19");
        when(inventoryService.create(any(Inventory.class))).thenReturn(entity);
        String json = "{\"inventoryId\":\"INV-101\",\"productId\":\"PROD-101\",\"stockQuantity\":100,\"warehouseLocation\":\"Aisle A\",\"lastUpdated\":\"2026-07-19\"}";
        mockMvc.perform(post("/inventory/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(entity.getInventoryId()));
    }

    @Test
    public void testRead() throws Exception {
        Inventory entity = InventoryFactory.createInventory("INV-101","PROD-101",100,"Aisle A","2026-07-19");
        when(inventoryService.read(entity.getInventoryId())).thenReturn(entity);
        mockMvc.perform(get("/inventory/read/" + entity.getInventoryId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(entity.getInventoryId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Inventory entity = InventoryFactory.createInventory("INV-101","PROD-101",100,"Aisle A","2026-07-19");
        when(inventoryService.update(any(Inventory.class))).thenReturn(entity);
        String json = "{\"inventoryId\":\"INV-101\",\"productId\":\"PROD-101\",\"stockQuantity\":100,\"warehouseLocation\":\"Aisle A\",\"lastUpdated\":\"2026-07-19\"}";
        mockMvc.perform(post("/inventory/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(entity.getInventoryId()));
    }

    @Test
    public void testDelete() throws Exception {
        Inventory entity = InventoryFactory.createInventory("INV-101","PROD-101",100,"Aisle A","2026-07-19");
        when(inventoryService.delete(entity.getInventoryId())).thenReturn(true);
        mockMvc.perform(delete("/inventory/delete/" + entity.getInventoryId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Inventory entity = InventoryFactory.createInventory("INV-101","PROD-101",100,"Aisle A","2026-07-19");
        when(inventoryService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/inventory/getall"))
                .andExpect(status().isOk());
    }
}

 */
