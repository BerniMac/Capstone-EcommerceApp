package service;
/*
cardService.java
author:isheanesu chowuraya 223182192
date 12 july 2026
 */
import domain.Card;
import java.util.List;
import java.util.Optional;

public interface CardService {
    Card save(Card card);

    Card update(String cardId, Card card);

    Optional<Card> findById(String cardId);

    List<Card> findAll();

     void delete(String cardId);
}
