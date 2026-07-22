package za.ca.cput.commerce.service.impl;

/*
Author: Tlangelani Chauke
19/07/2026
 */

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.repository.ShipmentRepository;
import za.ca.cput.commerce.service.ShipmentService;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    @Override
    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    @Override
    public Shipment findById(String id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment"+ id));
    }

    @Override
    public Shipment updateStatus(String id, String status) {
        Shipment existing = findById(id);

        Shipment updated = new Shipment.Builder()
                .copy(existing)
                .setStatus(status)
                .build();
        return shipmentRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        shipmentRepository.delete(findById(id));
    }
}
