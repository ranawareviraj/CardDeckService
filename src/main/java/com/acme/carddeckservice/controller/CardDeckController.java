package com.acme.carddeckservice.controller;

import com.acme.carddeckservice.model.Deck;
import com.acme.carddeckservice.service.CardDeckService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards/decks")
public class CardDeckController {

    CardDeckService cardDeckService;

    public CardDeckController(CardDeckService cardDeckService) {
        this.cardDeckService = cardDeckService;
    }

    @GetMapping("/new")
    public Deck createDeck() {
        return cardDeckService.createDeck();
    }

    @GetMapping("/{deckId}")
    public Deck getDeck(@PathVariable String deckId) {
        return CardDeckService.getDeck(deckId);
    }

    @PostMapping("/{deckId}/deal")
    public void dealCard(@PathVariable String deckId) {
        // TODO: Implement dealing logic
    }

    @PostMapping("/{deckId}/return")
    public void returnCard(@PathVariable String deckId) {
        // TODO: Implement returning logic
    }

    @PostMapping("/{deckId}/shuffle")
    public void shuffleDeck(@PathVariable String deckId) {
        // TODO: Implement shuffling logic
    }
}
