package repository.impl;
/*
cardRepository.java
author:isheanesu chowuraya 223182192
date:26 March 2026

import domain.Card;
import repository.CardRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {
    private final HashMap<String, Card> cardStore = new HashMap<>();

    @Override
    public Card create(Card card) {
        cardStore.put(card.getCardId(), card);
        return card;
    }

    @Override
    public Card read(String cardId) {
        return cardStore.get(cardId);
    }

    @Override
    public Card update(Card card) {
        cardStore.put(card.getCardId(), card);
        return card;
    }

    @Override
    public boolean delete(String cardId) {
        return cardStore.remove(cardId) != null;
    }

    @Override
    public List<Card> findAll() {
        return new ArrayList<>(cardStore.values());
    }
}
*/