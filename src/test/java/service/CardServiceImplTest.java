package service;

/*
Autor:isheanesu chowuraya(223182192)
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
import za.ca.cput.commerce.repository.CardRepository;
import za.ca.cput.commerce.service.impl.CardServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository repository;

    @InjectMocks
    private CardServiceImpl service;

    private Card card;

    @BeforeEach
    void setUp() {

        card = new Card.Builder()
                .setCardId("1")
                .setCardHolderName("John Smith")
                .setCardType("VISA")
                .setCardNumber("4111111111111111")
                .setCardExpiry("12/28")
                .setCardCVV("123")
                .build();
    }

    @Test
    void create() {

        when(repository.save(card)).thenReturn(card);

        Card created = service.create(card);

        assertNotNull(created);
        assertEquals("John Smith", created.getCardHolderName());

        verify(repository).save(card);
    }

    @Test
    void read() {

        when(repository.findById("1")).thenReturn(Optional.of(card));

        Card found = service.read("1");

        assertNotNull(found);
        assertEquals("1", found.getCardId());

        verify(repository).findById("1");
    }

    @Test
    void update() {

        when(repository.existsById("1")).thenReturn(true);
        when(repository.save(card)).thenReturn(card);

        Card updated = service.update(card);

        assertNotNull(updated);
        assertEquals("VISA", updated.getCardType());

        verify(repository).existsById("1");
        verify(repository).save(card);
    }

    @Test
    void delete() {

        doNothing().when(repository).deleteById("1");

        service.delete("1");

        verify(repository).deleteById("1");
    }

    @Test
    void getAll() {

        when(repository.findAll()).thenReturn(List.of(card));

        List<Card> cards = service.getAll();

        assertEquals(1, cards.size());

        verify(repository).findAll();
    }
}