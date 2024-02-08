package com.acme.carddeckservice.controller;

import com.acme.carddeckservice.model.Card;
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
        return cardDeckService.getDeck(deckId);
    }

    @GetMapping("/{deckId}/deal")
    public Card dealCard(@PathVariable String deckId) {
        return cardDeckService.dealCard(deckId);
    }

    @PostMapping("/{deckId}/return")
    public Card returnLastCard(@PathVariable String deckId) {
        return cardDeckService.returnLastCard(deckId);
    }

    @GetMapping("/{deckId}/shuffle")
    public void shuffleDeck(@PathVariable String deckId) {
        cardDeckService.shuffleDeck(deckId);
    }
}
