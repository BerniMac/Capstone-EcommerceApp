package service;

/*
cardServiceTest.java
author:isheanesu chowuraya 223182192
date 12 july 2026
 */
import domain.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CardRepository;
import service.impl.CardServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository repository;

    @InjectMocks
    private CardServiceImpl service;

    @Test
    void save() {

        Card card = new Card.Builder()
                .setCardHolderName("John")
                .build();

        when(repository.save(card)).thenReturn(card);

        Card saved = service.save(card);

        assertNotNull(saved);
        assertEquals("John", saved.getCardHolderName());

        verify(repository).save(card);
    }

    @Test
    void findById() {

        Card card = new Card.Builder()
                .setCardId("1")
                .setCardHolderName("John")
                .build();

        when(repository.findById("1")).thenReturn(Optional.of(card));

        Optional<Card> result = service.findById("1");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getCardHolderName());

        verify(repository).findById("1");
    }

    @Test
    void findAll() {

        Card card = new Card.Builder()
                .setCardHolderName("John")
                .build();

        when(repository.findAll()).thenReturn(List.of(card));

        List<Card> cards = service.findAll();

        assertEquals(1, cards.size());

        verify(repository).findAll();
    }

    @Test
    void update() {

        Card existing = new Card.Builder()
                .setCardId("1")
                .setCardHolderName("John")
                .setCardType("Visa")
                .build();

        Card updated = new Card.Builder()
                .setCardHolderName("Jane")
                .setCardType("MasterCard")
                .build();

        when(repository.findById("1")).thenReturn(Optional.of(existing));

        Card expected = new Card.Builder()
                .copy(existing)
                .setCardHolderName(updated.getCardHolderName())
                .setCardType(updated.getCardType())
                .build();

        when(repository.save(any(Card.class))).thenReturn(expected);

        Card result = service.update("1", updated);

        assertEquals("Jane", result.getCardHolderName());
        assertEquals("MasterCard", result.getCardType());

        verify(repository).findById("1");
        verify(repository).save(any(Card.class));
    }

    @Test
    void delete() {

        doNothing().when(repository).deleteById("1");

        service.delete("1");

        verify(repository).deleteById("1");
    }
}
