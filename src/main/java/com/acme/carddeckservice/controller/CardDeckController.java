package com.acme.carddeckservice.controller;

import com.acme.carddeckservice.error.ErrorResponse;
import com.acme.carddeckservice.error.InvalidInputException;
import com.acme.carddeckservice.error.NotFoundException;
import com.acme.carddeckservice.error.UnknownServerException;
import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import com.acme.carddeckservice.service.CardDeckService;
import com.acme.carddeckservice.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The CardDeckController class is a RESTful web service controller.
 * It handles requests related to card decks.
 *
 * @author Viraj Ranaware
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
     * Deals a card from a deck
     *
     * @param deckId The unique ID of the deck
     * @return Card The card object
     * @throws InvalidInputException, NotFoundException, UnknownServerException
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
     * Returns a card to a deck if it is a valid card and not already in the deck
     *
     * @param deckId The unique ID of the deck
     * @param card   The card object
     * @return ResponseEntity
     * @throws InvalidInputException           The input is not valid. The card is null, empty or already in the deck.
     * @throws NotFoundException               The requested resource (Card or Deck) is not found.
     * @throws UnknownServerException          Internal server error.
     * @throws HttpMessageNotReadableException The request body is missing or invalid.
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
     * Shuffles a deck
     *
     * @param deckId The unique ID of the deck
     * @throws InvalidInputException  The input is not valid. The deck ID is null or empty.
     * @throws NotFoundException      The requested deck is not found or is empty.
     * @throws UnknownServerException Internal server error.
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

    /**
     * Validate deck ID
     *
     * @param deckId The unique ID of the deck
     * @throws InvalidInputException The input is not valid. The deck ID is null or empty.
     * @throws NotFoundException     The requested deck is not found or is empty.
     */
    public void validateDeckId(String deckId) throws InvalidInputException, NotFoundException {
        if (deckId.isEmpty() || deckId.isBlank()) {
            throw new InvalidInputException("Deck id not valid");
        } else if (!cardDeckService.deckExists(deckId)) {
            throw new NotFoundException("Deck not found");
        } else if (cardDeckService.getDeck(deckId).getCards().isEmpty()) {
            throw new NotFoundException("Deck is empty");
        }
    }

    /**
     * Validate card
     *
     * @param card   The card object
     * @param deckId The unique ID of the deck
     * @throws InvalidInputException The input is not valid. The card is null, empty or already in the deck.
     * @throws NotFoundException     The requested resource (Card or Deck) is not found.
     */
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

    /**
     * Handle missing request body
     *
     * @return ResponseEntity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestBody() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setType(Constants.INVALID_REQUEST);
        errorResponse.setMessage("Invalid input - request body is missing or invalid");
        errorResponse.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
