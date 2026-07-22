package za.ca.cput.commerce.service.impl;

/*
Autor:isheanesu chowuraya(223182192)
19/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.repository.CardRepository;
import za.ca.cput.commerce.service.CardService;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findById(String id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card"+ id));
    }

    @Override
    public void deleteById(String id) {
        cardRepository.delete(findById(id));
    }
}
