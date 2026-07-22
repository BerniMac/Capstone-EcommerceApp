package service;

/*
Author: Joshua Jonathan Bird - 230444032
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
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.repository.OrderItemRepository;
import za.ca.cput.commerce.service.impl.OrderItemServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplUnitTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private OrderItem existingOrderItem;

    @BeforeEach
    void setUp() {
        existingOrderItem = new OrderItem.Builder()
                .setOrderItemId("item-1")
                .setOrderId("order-1")
                .setProductId("prod-1")
                .setQuantity(2)
                .setPriceAtPurchase(49.99)
                .build();
    }

    @Test
    void whenSave_thenReturnSavedOrderItem() {
        given(orderItemRepository.save(existingOrderItem)).willReturn(existingOrderItem);

        OrderItem saved = orderItemService.save(existingOrderItem);

        assertThat(saved).isEqualTo(existingOrderItem);
        verify(orderItemRepository, times(1)).save(existingOrderItem);
    }

    @Test
    void whenFindAll_thenReturnListOfOrderItems() {
        given(orderItemRepository.findAll()).willReturn(List.of(existingOrderItem));

        List<OrderItem> items = orderItemService.findAll();

        assertThat(items).hasSize(1).contains(existingOrderItem);
    }

    @Test
    void whenValidId_thenOrderItemShouldBeFound() {
        given(orderItemRepository.findById("item-1")).willReturn(Optional.of(existingOrderItem));

        OrderItem found = orderItemService.findById("item-1");

        assertThat(found.getOrderItemId()).isEqualTo("item-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(orderItemRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderItemService.findById("bad-id"));
    }

    @Test
    void whenUpdate_thenRebuiltOrderItemIsSavedWithNewFields() {
        OrderItem updateRequest = new OrderItem.Builder()
                .setQuantity(5)
                .setPriceAtPurchase(39.99)
                .build();

        given(orderItemRepository.findById("item-1")).willReturn(Optional.of(existingOrderItem));
        given(orderItemRepository.save(any(OrderItem.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        OrderItem result = orderItemService.update("item-1", updateRequest);

        ArgumentCaptor<OrderItem> captor = ArgumentCaptor.forClass(OrderItem.class);
        verify(orderItemRepository).save(captor.capture());
        OrderItem saved = captor.getValue();

        assertThat(saved.getOrderItemId()).isEqualTo("item-1");
        assertThat(saved.getOrderId()).isEqualTo("order-1");
        assertThat(saved.getQuantity()).isEqualTo(5);
        assertThat(saved.getPriceAtPurchase()).isEqualTo(39.99);
        assertThat(result).isEqualTo(saved);
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(orderItemRepository.findById("item-1")).willReturn(Optional.of(existingOrderItem));

        orderItemService.deleteById("item-1");

        verify(orderItemRepository, times(1)).delete(existingOrderItem);
    }
}
