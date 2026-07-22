package za.ca.cput.commerce.service.impl;

/*
Author: Plamedie 230082629
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.repository.InventoryRepository;
import za.ca.cput.commerce.service.InventoryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory findById(String id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory"+id));
    }

    @Override
    public Inventory update(String id, Inventory inventory) {
        Inventory existing = findById(id);

        Inventory updated = new Inventory.Builder()
                .copy(existing)
                .setStockQuantity(inventory.getStockQuantity())
                .setWarehouseLocation(inventory.getWarehouseLocation())
                .setLastUpdated(LocalDateTime.now().toString())
                .build();
        return inventoryRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        inventoryRepository.delete(findById(id));
    }
}