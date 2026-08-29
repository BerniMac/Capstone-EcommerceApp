package za.ac.cput.ecommerce.controller;
/*
 * ShipmentFactoryTest.java
 * Author: Tlangelani Chauke
 * Date:19 july 2026
*/
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import za.ca.cput.commerce.controller.ShipmentController;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.service.ShipmentService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShipmentController.class)
class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ShipmentService service;

    private Shipment shipment;
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order.Builder()
                .setOrderId("ORD001")
                .build();

        shipment = new Shipment.Builder()
                .setShipmentId("SHIP001")
                .setAddress("123 Main Street")
                .setShipmentDate(LocalDateTime.now())
                .setDeliveryDate(LocalDateTime.now().plusDays(3))
                .setStatus("Shipped")
                .setOrder(order)
                .build();
    }

    @Test
    void create() throws Exception {

        when(service.create(any(Shipment.class))).thenReturn(shipment);

        mockMvc.perform(post("/api/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shipment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value("SHIP001"))
                .andExpect(jsonPath("$.status").value("Shipped"));
    }

    @Test
    void read() throws Exception {

        when(service.read("SHIP001")).thenReturn(shipment);

        mockMvc.perform(get("/api/shipments/SHIP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value("SHIP001"))
                .andExpect(jsonPath("$.address").value("123 Main Street"));
    }

    @Test
    void read_NotFound() throws Exception {

        when(service.read("SHIP001")).thenReturn(null);

        mockMvc.perform(get("/api/shipments/SHIP001"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {

        when(service.read("SHIP001")).thenReturn(shipment);
        when(service.update(any(Shipment.class))).thenReturn(shipment);

        mockMvc.perform(put("/api/shipments/SHIP001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shipment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value("SHIP001"));
    }

    @Test
    void update_NotFound() throws Exception {

        when(service.read("SHIP001")).thenReturn(null);

        mockMvc.perform(put("/api/shipments/SHIP001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shipment)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {

        when(service.read("SHIP001")).thenReturn(shipment);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/shipments/SHIP001"))
                .andExpect(status().isNoContent());

        verify(service).delete("SHIP001");
    }

    @Test
    void delete_NotFound() throws Exception {

        when(service.read("SHIP001")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/shipments/SHIP001"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {

        when(service.getAll()).thenReturn(List.of(shipment));

        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shipmentId").value("SHIP001"));
    }

    @Test
    void getByOrderId() throws Exception {

        when(service.findByOrderId("ORD001")).thenReturn(shipment);

        mockMvc.perform(get("/api/shipments/order/ORD001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipmentId").value("SHIP001"));
    }

    @Test
    void getByOrderId_NotFound() throws Exception {

        when(service.findByOrderId("ORD001")).thenReturn(null);

        mockMvc.perform(get("/api/shipments/order/ORD001"))
                .andExpect(status().isNotFound());
    }
}

