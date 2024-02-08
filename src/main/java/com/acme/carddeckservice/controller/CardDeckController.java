package com.acme.carddeckservice.controller;

import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import com.acme.carddeckservice.service.CardDeckService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping("/api/cards-service/decks")
public class CardDeckController {

    CardDeckService cardDeckService;

    public CardDeckController(CardDeckService cardDeckService) {
        this.cardDeckService = cardDeckService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllDecks() {
        return cardDeckService.getAllDecks();
    }

    @GetMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Deck createDeck() {
        return cardDeckService.createDeck();
    }

    @GetMapping("/{deckId}")
    public Deck getDeck(@PathVariable String deckId) {
        cardDeckService.validateDeckId(deckId);
        return cardDeckService.getDeck(deckId);
    }

    @GetMapping("/{deckId}/deal")
    public Card dealCard(@PathVariable String deckId) {
        cardDeckService.validateDeckId(deckId);
        return cardDeckService.dealCard(deckId);
    }

    @PostMapping("/{deckId}/cards")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnCard(@PathVariable String deckId, @RequestBody Card card) {
        cardDeckService.validateDeckId(deckId);
        cardDeckService.validateCard(card, deckId);
        cardDeckService.returnCard(deckId, card);
    }

    @GetMapping("/{deckId}/shuffle")
    public void shuffleDeck(@PathVariable String deckId) {
        cardDeckService.validateDeckId(deckId);
        cardDeckService.shuffleDeck(deckId);
    }

}
