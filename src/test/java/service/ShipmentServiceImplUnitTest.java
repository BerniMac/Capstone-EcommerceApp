package service;

/*
2026/07/19
Author: Tlangelani Chauke
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.repository.ShipmentRepository;
import za.ca.cput.commerce.service.impl.ShipmentServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceImplUnitTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    private Shipment existingShipment;

    @BeforeEach
    void setUp() {
        existingShipment = new Shipment.Builder()
                .setShipmentId("ship-1")
                .setAddress("12 Main Road, Cape Town")
                .setShipmentDate(new Date())
                .setStatus("PROCESSING")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedShipment() {
        given(shipmentRepository.save(existingShipment)).willReturn(existingShipment);

        Shipment saved = shipmentService.save(existingShipment);

        assertThat(saved).isEqualTo(existingShipment);
        verify(shipmentRepository, times(1)).save(existingShipment);
    }

    @Test
    void whenFindAll_thenReturnListOfShipments() {
        given(shipmentRepository.findAll()).willReturn(List.of(existingShipment));

        List<Shipment> shipments = shipmentService.findAll();

        assertThat(shipments).hasSize(1).contains(existingShipment);
    }

    @Test
    void whenValidId_thenShipmentShouldBeFound() {
        given(shipmentRepository.findById("ship-1")).willReturn(Optional.of(existingShipment));

        Shipment found = shipmentService.findById("ship-1");

        assertThat(found.getShipmentId()).isEqualTo("ship-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(shipmentRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> shipmentService.findById("bad-id"));
    }

    @Test
    void whenUpdateStatus_thenRebuiltShipmentIsSavedWithNewStatus() {
        given(shipmentRepository.findById("ship-1")).willReturn(Optional.of(existingShipment));
        given(shipmentRepository.save(any(Shipment.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Shipment result = shipmentService.updateStatus("ship-1", "DELIVERED");

        ArgumentCaptor<Shipment> captor = ArgumentCaptor.forClass(Shipment.class);
        verify(shipmentRepository).save(captor.capture());
        Shipment saved = captor.getValue();

        assertThat(saved.getShipmentId()).isEqualTo("ship-1");
        assertThat(saved.getAddress()).isEqualTo("12 Main Road, Cape Town");
        assertThat(saved.getStatus()).isEqualTo("DELIVERED");
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(shipmentRepository.findById("ship-1")).willReturn(Optional.of(existingShipment));

        shipmentService.deleteById("ship-1");

        verify(shipmentRepository, times(1)).delete(existingShipment);
    }
}
