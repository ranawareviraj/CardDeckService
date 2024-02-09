package com.acme.carddeckservice.controller;

import com.acme.carddeckservice.error.InvalidInputException;
import com.acme.carddeckservice.error.NotFoundException;
import com.acme.carddeckservice.error.UnknownServerException;
import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import com.acme.carddeckservice.service.CardDeckService;
import com.acme.carddeckservice.utils.Constants;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The CardDeckController class is a RESTful web service controller.
 * It handles requests related to card decks.
 *
 * @author Viraj Ranaware
 */


@RestController
@RequestMapping("/api/cards-service/decks")
public class CardDeckController implements CardDeckAPI {

    public static final String X_REQUEST_ID = "X-Request-ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(CardDeckController.class);
    CardDeckService cardDeckService;

    public CardDeckController(CardDeckService cardDeckService) {
        this.cardDeckService = cardDeckService;
    }

    /**
     * Get all decks
     *
     * @return List of all deck IDs
     * @header X-Request-ID Optional unique ID of the request.
     * If provided, it will be also be returned in the response header.
     */
    @GetMapping()
    @Override
    public ResponseEntity<List<String>> getAllDeckIds(@RequestHeader(value = X_REQUEST_ID, required = false) String requestId) {
        requestId = requestId == null ? UUID.randomUUID().toString() : requestId;
        LOGGER.info("Received request to fetch all deck IDs, request Id: {}", requestId);
        List<String> decks = cardDeckService.getAllDeckIds();
        if (decks.isEmpty()) {
            LOGGER.info("No decks found, request Id: {}", requestId);
            throw new NotFoundException("No decks found");
        }

        LOGGER.info("Returning all deck IDs, request Id: {}", requestId);
        return ResponseEntity.status(HttpStatus.OK)
                .header(X_REQUEST_ID, requestId)
                .body(decks);
    }

    /**
     * Create a new deck
     *
     * @return Deck object
     * @header X-Request-ID Optional unique ID of the request.
     * If provided, it will be also be returned in the response header.
     */

    @GetMapping("/new")
    @Override
    public ResponseEntity<Deck> createDeck(@RequestHeader(value = X_REQUEST_ID, required = false) String requestId) {
        requestId = requestId == null ? UUID.randomUUID().toString() : requestId;
        LOGGER.info("Received request to create a new deck, request Id: {}", requestId);
        Deck deck = cardDeckService.createDeck();
        LOGGER.info("Returning response to create a new deck request with ID {}, request Id: {}", deck.getId(), requestId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(X_REQUEST_ID, requestId)
                .body(deck);
    }

    /**
     * Get a deck by ID
     *
     * @param deckId The unique ID of the deck
     * @return Deck The deck object
     * @header X-Request-ID Optional unique ID of the request.
     * If provided, it will be also be returned in the response header.
     */
    @GetMapping("/{deckId}")
    @Override
    public ResponseEntity<Deck> getDeck(@PathVariable String deckId,
                                        @RequestHeader(value = X_REQUEST_ID, required = false) String requestId) {
        requestId = requestId == null ? UUID.randomUUID().toString() : requestId;
        LOGGER.info("Received request to fetch deck for ID {}, request Id: {}", deckId, requestId);
        try {
            LOGGER.info("Validating deck ID {}, request Id: {}", deckId, requestId);
            validateDeckId(deckId);

            LOGGER.info("Validating deck state for deck ID {}, request Id: {}", deckId, requestId);
            validateDeckState(deckId);
        } catch (InvalidInputException e) {
            LOGGER.error("Invalid input: {}, request Id: {}", e.getMessage(), requestId);
            throw new InvalidInputException(e.getMessage());
        } catch (NotFoundException e) {
            LOGGER.error("Deck not found: {}, request Id: {}", deckId, requestId);
            throw new NotFoundException(e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Unknown error encountered: {}, request Id: {}", ex.getMessage(), requestId);
            throw new UnknownServerException("Internal server error occurred. Please try again later.");
        }

        Deck deck = cardDeckService.getDeck(deckId);
        LOGGER.info("Returning deck for ID {}, request Id: {}", deckId, requestId);
        LOGGER.debug("Deck: {}, request Id: {}", deck.toString(), requestId);
        return ResponseEntity.status(HttpStatus.OK)
                .header(X_REQUEST_ID, requestId)
                .body(deck);
    }

    /**
     * Deals a card from a deck
     *
     * @param deckId The unique ID of the deck
     * @return Card The card object
     * @throws InvalidInputException  The input is not valid. The deck ID is null or empty.
     * @throws NotFoundException      The requested deck is not found or is empty.
     * @throws UnknownServerException Internal server error.
     * @header X-Request-ID Optional unique ID of the request.
     * If provided, it will be also be returned in the response header.
     */
    @GetMapping("/{deckId}/deal")
    @Override
    public ResponseEntity<Card> dealCard(@PathVariable String deckId,
                                         @RequestHeader(value = X_REQUEST_ID, required = false) String requestId) {
        requestId = requestId == null ? UUID.randomUUID().toString() : requestId;
        LOGGER.info("Received request to deal a card from deck {}, request Id: {}", deckId, requestId);
        try {
            LOGGER.info("Validating deck ID {}, request Id: {}", deckId, requestId);
            validateDeckId(deckId);

            LOGGER.info("Validating deck state for deck ID {}, request Id: {}", deckId, requestId);
            validateDeckState(deckId);
        } catch (InvalidInputException e) {
            LOGGER.error("Invalid input: {}, request Id: {}", e.getMessage(), requestId);
            throw new InvalidInputException(e.getMessage());
        } catch (NotFoundException e) {
            LOGGER.error("Deck not found: {}, request Id: {}", deckId, requestId);
            throw new NotFoundException(e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Unknown error encountered: {}, request Id: {}", ex.getMessage(), requestId);
            throw new UnknownServerException("Internal server error occurred. Please try again later.");
        }

        Card card = cardDeckService.dealCard(deckId);
        LOGGER.info("Dealing a card from deck {}, request Id: {}", deckId, requestId);
        LOGGER.debug("Returning card {}, request Id: {}", card, requestId);

        return ResponseEntity.status(HttpStatus.OK)
                .header(X_REQUEST_ID, requestId)
                .body(card);
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
     * @header X-Request-ID Optional unique ID of the request.
     * If provided, it will be also be returned in the response header.
     */
    @PostMapping("/{deckId}/cards")
    @Override
    public ResponseEntity<Void> returnCard(@PathVariable String deckId, @RequestBody @Valid Card card,
                                           @RequestHeader(value = X_REQUEST_ID, required = false) String requestId) {
        requestId = requestId == null ? UUID.randomUUID().toString() : requestId;
        LOGGER.info("Received request to return card to deck {}, request Id: {}", deckId, requestId);
        LOGGER.debug("Card to return: {}, request Id: {}", card, requestId);
        try {
            LOGGER.info("Validating deck ID {}, request Id: {}", deckId, requestId);
            validateDeckId(deckId);

            LOGGER.info("Validating card, request Id: {}", requestId);
            validateCard(card, deckId);
            LOGGER.debug("Returning card to deck {}, request Id: {}", card, requestId);
            cardDeckService.returnCard(deckId, card);
        } catch (InvalidInputException e) {
            LOGGER.error("Invalid input: {}, request Id: {}", e.getMessage(), requestId);
            throw new InvalidInputException(e.getMessage());
        } catch (NotFoundException e) {
            LOGGER.error("Deck not found: {}, request Id: {}", deckId, requestId);
            throw new NotFoundException(e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Unknown error encountered: {}, request Id: {}", ex.getMessage(), requestId);
            throw new UnknownServerException("Internal server error occurred. Please try again later.");
        }

        LOGGER.info("Card returned to deck {}, request Id: {}", deckId, requestId);
        LOGGER.debug("Card returned: {}, request Id: {}", card, requestId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header(X_REQUEST_ID, requestId)
                .build();
    }

    /**
     * Shuffles a deck
     *
     * @param deckId The unique ID of the deck
     * @throws InvalidInputException  The input is not valid. The deck ID is null or empty.
     * @throws NotFoundException      The requested deck is not found or is empty.
     * @throws UnknownServerException Internal server error.
     * @header X-Request-ID Optional unique ID of the request.
     * If provided, it will be also be returned in the response header.
     */

    @GetMapping("/{deckId}/shuffle")
    @Override
    public ResponseEntity<Void> shuffleDeck(@PathVariable String deckId,
                                            @RequestHeader(value = X_REQUEST_ID, required = false) String requestId) {
        requestId = requestId == null ? UUID.randomUUID().toString() : requestId;
        LOGGER.info("Received request to shuffle deck {}, request Id: {}", deckId, requestId);
        try {
            LOGGER.info("Validating deck ID {}, request Id: {}", deckId, requestId);
            validateDeckId(deckId);

            LOGGER.info("Shuffling deck {}, request Id: {}", deckId, requestId);
            cardDeckService.shuffleDeck(deckId);
        } catch (InvalidInputException e) {
            LOGGER.error("Invalid input: {}, request Id: {}", e.getMessage(), requestId);
            throw new InvalidInputException(e.getMessage());
        } catch (NotFoundException e) {
            LOGGER.error("Deck not found: {}, request Id: {}", deckId, requestId);
            throw new NotFoundException(e.getMessage());
        } catch (Exception ex) {
            throw new UnknownServerException("Internal server error occurred. Please try again later.");
        }

        LOGGER.info("Deck shuffled {}, request Id: {}", deckId, requestId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header(X_REQUEST_ID, requestId)
                .build();
    }

    /**
     * Validate deck ID
     *
     * @param deckId The unique ID of the deck
     * @throws InvalidInputException The input is not valid. The deck ID is null or empty.
     * @throws NotFoundException     The requested deck is not found or is empty.
     * @header X-Request-ID Optional unique ID of the request.
     * If provided, it will be also be returned in the response header.
     */
    public void validateDeckId(String deckId) throws InvalidInputException, NotFoundException {
        if (deckId.isEmpty() || deckId.isBlank()) {
            throw new InvalidInputException("Deck id not valid");
        } else if (!cardDeckService.deckExists(deckId)) {
            throw new NotFoundException("Deck not found");
        }
    }

    public void validateDeckState(String deckId) throws NotFoundException {
        if (cardDeckService.getDeck(deckId).getCards().isEmpty()) {
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
     * @header X-Request-ID Optional unique ID of the request.
     * If provided, it will be also be returned in the response header.
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
                    .append(", valid suits are: ")
                    .append(Constants.SUITS)
                    .toString());
        }

        if (!Constants.RANKS.contains(card.getRank())) {
            throw new InvalidInputException(new StringBuilder()
                    .append("Invalid rank: ")
                    .append(card.getRank())
                    .append(", valid ranks are:")
                    .append(Constants.RANKS)
                    .toString());
        }

        if (cardDeckService.getAllDecks().get(deckId).getCards().contains(card)) {
            throw new InvalidInputException("Card already in deck - cannot return card that is already in deck");
        }
    }
}
