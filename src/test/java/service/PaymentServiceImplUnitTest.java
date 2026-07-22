package service;

/*
Author: Mogamad Jawaad Allie - 230472125
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Payment;
import za.ca.cput.commerce.repository.PaymentRepository;
import za.ca.cput.commerce.service.impl.PaymentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplUnitTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment existingPayment;

    @BeforeEach
    void setUp() {
        existingPayment = new Payment.Builder()
                .setPaymentId("pay-1")
                .setOrderId("order-1")
                .setPaymentAmount(100.00)
                .setPaymentDate("2026-01-01")
                .setPaymentMethod("CARD")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedPayment() {
        given(paymentRepository.save(existingPayment)).willReturn(existingPayment);

        Payment saved = paymentService.save(existingPayment);

        assertThat(saved).isEqualTo(existingPayment);
        verify(paymentRepository, times(1)).save(existingPayment);
    }

    @Test
    void whenFindAll_thenReturnListOfPayments() {
        given(paymentRepository.findAll()).willReturn(List.of(existingPayment));

        List<Payment> payments = paymentService.findAll();

        assertThat(payments).hasSize(1).contains(existingPayment);
    }

    @Test
    void whenValidId_thenPaymentShouldBeFound() {
        given(paymentRepository.findById("pay-1")).willReturn(Optional.of(existingPayment));

        Payment found = paymentService.findById("pay-1");

        assertThat(found.getPaymentId()).isEqualTo("pay-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(paymentRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> paymentService.findById("bad-id"));
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(paymentRepository.findById("pay-1")).willReturn(Optional.of(existingPayment));

        paymentService.deleteById("pay-1");

        verify(paymentRepository, times(1)).delete(existingPayment);
    }
}
