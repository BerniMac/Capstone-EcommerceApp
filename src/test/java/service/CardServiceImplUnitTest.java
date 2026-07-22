package service;

/*
Autor:isheanesu chowuraya(223182192)
19/07/2026
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardServiceImplUnitTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card existingCard;

    @BeforeEach
    void setUp() {
        existingCard = new Card.Builder()
                .setCardId("card-1")
                .setCardHolderName("Anesu M")
                .setCardType("VISA")
                .setCardNumber("4111111111111111")
                .setCardExpiry("12/28")
                .setCardCVV("123")
                .build();
    }

    @Test
    void whenSave_thenReturnSavedCard() {
        given(cardRepository.save(existingCard)).willReturn(existingCard);

        Card saved = cardService.save(existingCard);

        assertThat(saved).isEqualTo(existingCard);
        verify(cardRepository, times(1)).save(existingCard);
    }

    @Test
    void whenFindAll_thenReturnListOfCards() {
        given(cardRepository.findAll()).willReturn(List.of(existingCard));

        List<Card> cards = cardService.findAll();

        assertThat(cards).hasSize(1).contains(existingCard);
    }

    @Test
    void whenValidId_thenCardShouldBeFound() {
        given(cardRepository.findById("card-1")).willReturn(Optional.of(existingCard));

        Card found = cardService.findById("card-1");

        assertThat(found.getCardId()).isEqualTo("card-1");
    }

    @Test
    void whenInvalidId_thenThrowResourceNotFoundException() {
        given(cardRepository.findById("bad-id")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardService.findById("bad-id"));
    }

    @Test
    void whenDeleteById_thenRepositoryDeleteIsInvoked() {
        given(cardRepository.findById("card-1")).willReturn(Optional.of(existingCard));

        cardService.deleteById("card-1");

        verify(cardRepository, times(1)).delete(existingCard);
    }
}
