package za.ca.cput.commerce.service;

/*
Author: Tlangelani Chauke
19/07/2026
 */
import za.ca.cput.commerce.domain.Shipment;

import java.util.List;

public interface ShipmentService {
    Shipment save(Shipment shipment);
    List<Shipment> findAll();
    Shipment findById(String id);
    Shipment updateStatus(String id, String status);
    void deleteById(String id);
}
