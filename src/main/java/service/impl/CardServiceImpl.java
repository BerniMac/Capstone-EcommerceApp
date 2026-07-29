package service.impl;
/*
cardService.java
author:isheanesu chowuraya 223182192
date 12 july 2026
 */
import domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CardRepository;
import service.CardService;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository repository;

    @Autowired
    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card save(Card card) {
        return repository.save(card);
    }

    @Override
    public Card update(String cardId, Card card) {

        Card existingCard = repository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        Card updatedCard = new Card.Builder()
                .copy(existingCard)
                .setCardHolderName(card.getCardHolderName())
                .setCardType(card.getCardType())
                .setCardNumber(card.getCardNumber())
                .setCardExpiry(card.getCardExpiry())
                .setCardCVV(card.getCardCVV())
                .build();

        return repository.save(updatedCard);
    }

    @Override
    public Optional<Card> findById(String cardId) {
        return repository.findById(cardId);
    }

    @Override
    public List<Card> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String cardId) {
        repository.deleteById(cardId);
    }
}
