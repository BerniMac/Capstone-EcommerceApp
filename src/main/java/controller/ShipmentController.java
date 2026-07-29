package controller;
/*
import domain.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.ShipmentService;

import java.util.List;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/create")
    public Shipment create(@RequestBody Shipment entity) {
        return shipmentService.create(entity);
    }

    @GetMapping("/read/{id}")
    public Shipment read(@PathVariable String id) {
        return shipmentService.read(id);
    }

    @PostMapping("/update")
    public Shipment update(@RequestBody Shipment entity) {
        return shipmentService.update(entity);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable String id) {
        return shipmentService.delete(id);
    }

    @GetMapping("/getall")
    public List<Shipment> getAll() {
        return shipmentService.findAll();
    }
}


 */