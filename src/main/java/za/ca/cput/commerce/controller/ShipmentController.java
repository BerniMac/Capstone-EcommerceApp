package za.ca.cput.commerce.controller;

/*
Author: Tlangelani Chauke
19/07/2026
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.service.ShipmentService;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public List<Shipment> getAllShipments() {
        return shipmentService.findAll();
    }

    @GetMapping("/{id}")
    public Shipment getShipmentById(@PathVariable String id) {
        return shipmentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shipment createShipment(@RequestBody Shipment shipment) {
        return shipmentService.save(shipment);
    }

    @PatchMapping("/{id}/status")
    public Shipment updateShipmentStatus(@PathVariable String id, @RequestParam String status) {
        return shipmentService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShipment(@PathVariable String id) {
        shipmentService.deleteById(id);
    }
}
