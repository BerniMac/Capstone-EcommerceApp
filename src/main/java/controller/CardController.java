package controller;
/*
cardController.java
author:isheanesu chowuraya 223182192
date 19 july 2026
 */
import domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CardService;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        return new ResponseEntity<>(service.save(card), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable String id,
                                           @RequestBody Card card) {
        return ResponseEntity.ok(service.update(id, card));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}