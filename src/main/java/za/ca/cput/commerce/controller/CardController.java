package za.ca.cput.commerce.controller;

/*
author:isheanesu chowuraya(223182192)
19/07/2026
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.findAll();
    }

    @GetMapping("/{id}")
    public Card getCardById(@PathVariable String id) {
        return cardService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Card createCard(@RequestBody Card card) {
        return cardService.save(card);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable String id) {
        cardService.deleteById(id);
    }
}
