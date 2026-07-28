package controller;
/*
import domain.Shipment;
import factory.ShipmentFactory;
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
import service.ShipmentService;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ShipmentController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {ShipmentController.class, ShipmentControllerTest.MockServiceConfig.class})
public class ShipmentControllerTest {

    @Configuration
    public static class MockServiceConfig {
        @Bean
        public ShipmentService shipmentService() {
            return org.mockito.Mockito.mock(ShipmentService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShipmentService shipmentService;

    @Test
    public void testCreate() throws Exception {
        Shipment entity = ShipmentFactory.createShipment("123 Main St",new java.util.Date(),new java.util.Date(),"Shipped");
        when(shipmentService.create(any(Shipment.class))).thenReturn(entity);
        String json = "{\"shipmentId\":\"SHIP-101\",\"address\":\"123 Main St\",\"status\":\"Shipped\"}";
        mockMvc.perform(post("/shipment/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value(entity.getShipmentId()));
    }

    @Test
    public void testRead() throws Exception {
        Shipment entity = ShipmentFactory.createShipment("123 Main St",new java.util.Date(),new java.util.Date(),"Shipped");
        when(shipmentService.read(entity.getShipmentId())).thenReturn(entity);
        mockMvc.perform(get("/shipment/read/" + entity.getShipmentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value(entity.getShipmentId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Shipment entity = ShipmentFactory.createShipment("123 Main St",new java.util.Date(),new java.util.Date(),"Shipped");
        when(shipmentService.update(any(Shipment.class))).thenReturn(entity);
        String json = "{\"shipmentId\":\"SHIP-101\",\"address\":\"123 Main St\",\"status\":\"Shipped\"}";
        mockMvc.perform(post("/shipment/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value(entity.getShipmentId()));
    }

    @Test
    public void testDelete() throws Exception {
        Shipment entity = ShipmentFactory.createShipment("123 Main St",new java.util.Date(),new java.util.Date(),"Shipped");
        when(shipmentService.delete(entity.getShipmentId())).thenReturn(true);
        mockMvc.perform(delete("/shipment/delete/" + entity.getShipmentId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetAll() throws Exception {
        Shipment entity = ShipmentFactory.createShipment("123 Main St",new java.util.Date(),new java.util.Date(),"Shipped");
        when(shipmentService.findAll()).thenReturn(Arrays.asList(entity));
        mockMvc.perform(get("/shipment/getall"))
                .andExpect(status().isOk());
    }
}

 */
