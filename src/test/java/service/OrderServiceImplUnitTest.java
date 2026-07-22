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
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.repository.OrderRepository;
import za.ca.cput.commerce.service.impl.OrderServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order existingOrder;

    @BeforeEach
    void setUp() {
        existingOrder = new Order.Builder()
                .setOrderId("order-1")
                .setCustomerId("cust-1")
                .setOrderDate("2026-01-01")
                .setTotalAmount(100.00)
                .build();
    }

    @Test
    void whenSave_thenReturnSavedOrder() {
        given(orderRepository.save(existingOrder)).willReturn(existingOrder);

        Order saved = orderService.save(existingOrder);

        assertThat(saved).isEqualTo(existingOrder);
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    void whenFindAll_thenReturnListOfOrders() {
        given(orderRepository.findAll()).willReturn(List.of(existingOrder));

        List<Order> orders = orderService.findAll();

        assertThat(orders).hasSize(1).contains(existingOrder);
    }

    @Test
    void whenValidId_thenOrderShouldBeFound() {
        given(orderRepository.findById("order-1")).willReturn(Optional.of(existingOrder));

        Order found = orderService.findById("order-1");

        assertThat(found.getOrderId()).isEqualTo("order-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(orderRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.findById("bad-id"));
    }

//    @Test
//    void whenUpdate_thenRebuiltOrderIsSavedWithNewTotalAmount() {
//        Order updateRequest = new Order.Builder()
//                .setTotalAmount(250.00)
//                .build();
//
//        given(orderRepository.findById("order-1")).willReturn(Optional.of(existingOrder));
//        given(orderRepository.save(any(Order.class)))
//                .willAnswer(invocation -> invocation.getArgument(0));
//
//        Order result = orderService.update("order-1", updateRequest);
//
//        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
//        verify(orderRepository).save(captor.capture());
//        Order saved = captor.getValue();
//
//        assertThat(saved.getOrderId()).isEqualTo("order-1");
//        assertThat(saved.getCustomerId()).isEqualTo("cust-1");
//        assertThat(saved.getTotalAmount()).isEqualTo(250.00);
//        assertThat(result).isEqualTo(saved);
//    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(orderRepository.findById("order-1")).willReturn(Optional.of(existingOrder));

        orderService.deleteById("order-1");

        verify(orderRepository, times(1)).delete(existingOrder);
    }
}
