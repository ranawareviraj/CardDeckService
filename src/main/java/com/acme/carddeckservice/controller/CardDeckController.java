package com.acme.carddeckservice.controller;

import com.acme.carddeckservice.error.InvalidInputException;
import com.acme.carddeckservice.error.NotFoundException;
import com.acme.carddeckservice.error.UnknownServerException;
import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import com.acme.carddeckservice.service.CardDeckService;
import com.acme.carddeckservice.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * The CardDeckController class is a RESTful web service controller.
 * It handles requests related to card decks.
 */

@RestController
@RequestMapping("/api/cards-service/decks")
public class CardDeckController {

    CardDeckService cardDeckService;

    public CardDeckController(CardDeckService cardDeckService) {
        this.cardDeckService = cardDeckService;
    }

    /**
     * Get all decks
     *
     * @return List of all deck IDs
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllDeckIds() {
        List<String> decks = cardDeckService.getAllDeckIds();
        if (decks.isEmpty()) {
            throw new NotFoundException("No decks found");
        }
        return decks;
    }

    /**
     * Create a new deck
     *
     * @return Deck object
     */
    @GetMapping("/new")
    public ResponseEntity<Deck> createDeck() {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardDeckService.createDeck());
    }

    /**
     * Get a deck by ID
     *
     * @param deckId The unique ID of the deck
     * @return Deck The deck object
     */
    @GetMapping("/{deckId}")
    public ResponseEntity<Deck> getDeck(@PathVariable String deckId) {
        try {
            validateDeckId(deckId);
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception ex) {
            throw new UnknownServerException("Internal server error occurred. Please try again later.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cardDeckService.getDeck(deckId));
    }

    /**
     * Deal a card from a deck
     *
     * @param deckId The unique ID of the deck
     * @return Card The card object
     */

    @GetMapping("/{deckId}/deal")
    public ResponseEntity<Card> dealCard(@PathVariable String deckId) {
        try {
            validateDeckId(deckId);
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception ex) {
            throw new UnknownServerException("Internal server error occurred. Please try again later.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cardDeckService.dealCard(deckId));
    }

    /**
     * Return a card to a deck
     *
     * @param deckId The unique ID of the deck
     * @param card   The card object
     */
    @PostMapping("/{deckId}/cards")
    public ResponseEntity<Object> returnCard(@PathVariable String deckId, @RequestBody Card card) {
        try {
            validateDeckId(deckId);
            validateCard(card, deckId);
            cardDeckService.returnCard(deckId, card);
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception ex) {
            throw new UnknownServerException("Internal server error occurred. Please try again later.");
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Shuffle a deck
     *
     * @param deckId The unique ID of the deck
     */
    @GetMapping("/{deckId}/shuffle")
    public ResponseEntity<Object> shuffleDeck(@PathVariable String deckId) {
        try {
            validateDeckId(deckId);
            cardDeckService.shuffleDeck(deckId);
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception ex) {
            throw new UnknownServerException("Internal server error occurred. Please try again later.");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public void validateDeckId(String deckId) throws InvalidInputException, NotFoundException {
        if (deckId.isEmpty() || deckId.isBlank()) {
            throw new InvalidInputException("Deck id not valid");
        } else if (!cardDeckService.deckExists(deckId)) {
            throw new NotFoundException("Deck not found");
        } else if (cardDeckService.getDeck(deckId).getCards().isEmpty()) {
            throw new NotFoundException("Deck is empty");
        }
    }

    public void validateCard(Card card, String deckId) throws InvalidInputException, NotFoundException {
        if (card == null) {
            throw new InvalidInputException("Card cannot be null or empty");
        }

        if (card.getSuit() == null || card.getRank() == null) {
            throw new InvalidInputException("Invalid card - suit and rank must be provided");
        }

        if (card.getSuit().isEmpty() || card.getSuit().isBlank()) {
            throw new InvalidInputException("Invalid card - suit cannot be empty");
        }

        if (card.getRank().isEmpty() || card.getRank().isBlank()) {
            throw new InvalidInputException("Invalid card - rank cannot be empty");
        }

        if (!Constants.SUITS.contains(card.getSuit())) {
            throw new InvalidInputException(new StringBuilder()
                    .append("Invalid suit: ")
                    .append(card.getSuit())
                    .append(", valid suits are Hearts, Diamonds, Clubs, Spades")
                    .toString());
        }

        if (!Constants.RANKS.contains(card.getRank())) {
            throw new InvalidInputException(new StringBuilder()
                    .append("Invalid rank: ")
                    .append(card.getRank())
                    .append(", valid ranks are 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace")
                    .toString());
        }

        if (cardDeckService.getAllDecks().get(deckId).getCards().contains(card)) {
            throw new InvalidInputException("Card already in deck - cannot return card that is already in deck");
        }
    }

}
