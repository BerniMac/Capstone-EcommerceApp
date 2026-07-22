package service;

/*
Author: Plamedie 230082629
19/07/2026
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
import za.ca.cput.commerce.repository.InventoryRepository;
import za.ca.cput.commerce.service.impl.InventoryServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplUnitTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory existingInventory;

    @BeforeEach
    void setUp() {
        existingInventory = new Inventory.Builder()
                .setInventoryId("inv-1")
                .setProductId("prod-1")
                .setStockQuantity(50)
                .setWarehouseLocation("Cape Town DC")
                .setLastUpdated("2026-01-01T00:00:00")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedInventory() {
        given(inventoryRepository.save(existingInventory)).willReturn(existingInventory);

        Inventory saved = inventoryService.save(existingInventory);

        assertThat(saved).isEqualTo(existingInventory);
        verify(inventoryRepository, times(1)).save(existingInventory);
    }

    @Test
    void whenFindAll_thenReturnListOfInventory() {
        given(inventoryRepository.findAll()).willReturn(List.of(existingInventory));

        List<Inventory> inventory = inventoryService.findAll();

        assertThat(inventory).hasSize(1).contains(existingInventory);
    }

    @Test
    void whenValidId_thenInventoryShouldBeFound() {
        given(inventoryRepository.findById("inv-1")).willReturn(Optional.of(existingInventory));

        Inventory found = inventoryService.findById("inv-1");

        assertThat(found.getInventoryId()).isEqualTo("inv-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(inventoryRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.findById("bad-id"));
    }

    @Test
    void whenUpdate_thenRebuiltInventoryIsSavedWithNewFields() {
        Inventory updateRequest = new Inventory.Builder()
                .setStockQuantity(75)
                .setWarehouseLocation("Johannesburg DC")
                .build();

        given(inventoryRepository.findById("inv-1")).willReturn(Optional.of(existingInventory));
        given(inventoryRepository.save(any(Inventory.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Inventory result = inventoryService.update("inv-1", updateRequest);

        ArgumentCaptor<Inventory> captor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryRepository).save(captor.capture());
        Inventory saved = captor.getValue();

        assertThat(saved.getInventoryId()).isEqualTo("inv-1");
        assertThat(saved.getProductId()).isEqualTo("prod-1");
        assertThat(saved.getStockQuantity()).isEqualTo(75);
        assertThat(saved.getWarehouseLocation()).isEqualTo("Johannesburg DC");
        assertThat(saved.getLastUpdated()).isNotNull();
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(inventoryRepository.findById("inv-1")).willReturn(Optional.of(existingInventory));

        inventoryService.deleteById("inv-1");

        verify(inventoryRepository, times(1)).delete(existingInventory);
    }
}

