package service;

/*
Author: Plamedie 230082629
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.repository.InventoryRepository;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.impl.InventoryServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Product product;
    private Inventory inventory;

    @BeforeEach
    void setUp() {

        product = new Product.Builder()
                .setProductId("P001")
                .setProductName("Laptop")
                .build();

        inventory = new Inventory.Builder()
                .setInventoryId("I001")
                .setProduct(product)
                .setStockQuantity(25)
                .setWarehouseLocation("Warehouse A")
                .setLastUpdated(LocalDateTime.now())
                .build();
    }

    @Test
    void createInventory() {

        when(productRepository.findById("P001"))
                .thenReturn(Optional.of(product));

        when(inventoryRepository.existsByProduct(product))
                .thenReturn(false);

        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(inventory);

        Inventory saved = inventoryService.createInventory(inventory);

        assertNotNull(saved);
        assertEquals(25, saved.getStockQuantity());

        verify(productRepository).findById("P001");
        verify(inventoryRepository).existsByProduct(product);
        verify(inventoryRepository).save(any(Inventory.class));
    }

    @Test
    void getInventoryById() {

        when(inventoryRepository.findById("I001"))
                .thenReturn(Optional.of(inventory));

        Inventory found = inventoryService.getInventoryById("I001");

        assertEquals("I001", found.getInventoryId());

        verify(inventoryRepository).findById("I001");
    }

    @Test
    void getAllInventory() {

        when(inventoryRepository.findAll())
                .thenReturn(List.of(inventory));

        List<Inventory> inventories =
                inventoryService.getAllInventory();

        assertEquals(1, inventories.size());

        verify(inventoryRepository).findAll();
    }

    @Test
    void updateInventory() {

        when(inventoryRepository.findById("I001"))
                .thenReturn(Optional.of(inventory));

        when(productRepository.findById("P001"))
                .thenReturn(Optional.of(product));

        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(inventory);

        Inventory updated =
                inventoryService.updateInventory("I001", inventory);

        assertNotNull(updated);

        verify(inventoryRepository).save(any(Inventory.class));
    }

    @Test
    void deleteInventory() {

        when(inventoryRepository.findById("I001"))
                .thenReturn(Optional.of(inventory));

        inventoryService.deleteInventory("I001");

        verify(inventoryRepository).delete(inventory);
    }

    @Test
    void getInventoryByProductId() {

        when(inventoryRepository.findByProduct_ProductId("P001"))
                .thenReturn(Optional.of(inventory));

        Inventory found =
                inventoryService.getInventoryByProductId("P001");

        assertNotNull(found);
        assertEquals("P001", found.getProduct().getProductId());

        verify(inventoryRepository).findByProduct_ProductId("P001");
    }
}