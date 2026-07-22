package za.ca.cput.commerce.service;

/*
Autor:isheanesu chowuraya(223182192)
19/07/2026
 */

import za.ca.cput.commerce.domain.Card;

import java.util.List;

public interface CardService {
    Card save(Card card);
    List<Card> findAll();
    Card findById(String id);
    void deleteById(String id);
}
