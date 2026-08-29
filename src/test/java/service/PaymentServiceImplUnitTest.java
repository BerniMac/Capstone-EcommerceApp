package service;

/*
Author: Mogamad Jawaad Allie - 230472125
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Payment;
import za.ca.cput.commerce.repository.PaymentRepository;
import za.ca.cput.commerce.service.impl.PaymentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplUnitTest {

    @Mock
    private PaymentRepository repository;

    @InjectMocks
    private PaymentServiceImpl service;

    private Order order;
    private Payment payment;
    private Card card;
    @BeforeEach
    void setUp() {

        order = new Order.Builder()
                .setOrderId("ORD001")
                .build();

        card = new Card.Builder()
                .setCardId("CARD001")
                .build();

        payment = new Payment.Builder()
                .setPaymentId("PAY001")
                .setOrder(order)
                .setPaymentAmount(2500)
                .setPaymentMethod("Credit Card")
                .setCard(card)
                .build();
    }

    @Test
    void create() {

        when(repository.save(payment)).thenReturn(payment);

        Payment created = service.create(payment);

        assertNotNull(created);
        assertEquals("PAY001", created.getPaymentId());

        verify(repository).save(payment);
    }

    @Test
    void getById() {

        when(repository.findById("PAY001"))
                .thenReturn(Optional.of(payment));

        Payment found = service.getById("PAY001");

        assertEquals(payment, found);

        verify(repository).findById("PAY001");
    }

    @Test
    void getAll() {

        List<Payment> list = List.of(payment);

        when(repository.findAll()).thenReturn(list);

        List<Payment> result = service.getAll();

        assertEquals(1, result.size());

        verify(repository).findAll();
    }

    @Test
    void update() {

        Payment updated = new Payment.Builder()
                .copy(payment)
                .setPaymentAmount(3000)
                .build();

        when(repository.findById("PAY001"))
                .thenReturn(Optional.of(payment));

        when(repository.save(any(Payment.class)))
                .thenReturn(updated);

        Payment result = service.update("PAY001", updated);

        assertEquals(3000, result.getPaymentAmount());

        verify(repository).findById("PAY001");
        verify(repository).save(any(Payment.class));
    }

    @Test
    void delete() {

        doNothing().when(repository).deleteById("PAY001");

        service.delete("PAY001");

        verify(repository).deleteById("PAY001");
    }
    @Test
    void getByCard() {

        List<Payment> list = List.of(payment);

        when(repository.findByCardCardId("CARD001"))
                .thenReturn(list);

        List<Payment> result = service.getByCard("CARD001");

        assertEquals(1, result.size());

        verify(repository).findByCardCardId("CARD001");
    }
    @Test
    void getByOrder() {

        when(repository.findByOrderOrderId("ORD001"))
                .thenReturn(payment);

        Payment result = service.getByOrder("ORD001");

        assertEquals("PAY001", result.getPaymentId());

        verify(repository).findByOrderOrderId("ORD001");
    }
}
