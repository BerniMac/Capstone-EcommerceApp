package za.ca.cput.commerce.repository;

/*
Author: Tlangelani Chauke
19/07/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}
