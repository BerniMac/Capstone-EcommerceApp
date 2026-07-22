package za.ca.cput.commerce.service;

/*
Author: Plamedie 230082629
19/07/2026
 */


import za.ca.cput.commerce.domain.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory save(Inventory inventory);
    List<Inventory> findAll();
    Inventory findById(String id);
    Inventory update(String id, Inventory inventory);
    void deleteById(String id);
}
