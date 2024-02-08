package com.acme.carddeckservice.service;

import com.acme.carddeckservice.utils.Constants;
import com.acme.carddeckservice.error.InvalidInputException;
import com.acme.carddeckservice.error.NotFoundException;
import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The CardDeckService class is a service class that provides methods to create, get, shuffle, deal and return cards to a deck.
 */
@Service
public class CardDeckService {
    private static final Map<String, Deck> decks = new ConcurrentHashMap<>();
    long count = 0;

    public Deck createDeck() {
        // Initialize deck with 52 cards
        List<Card> cards = new ArrayList<>();
        for (String suit : Constants.SUITS) {
            for (String rank : Constants.RANKS) {
                cards.add(new Card(suit, rank));
            }
        }
        count++;
        String deckId = String.valueOf(count);
        Deck deck = new Deck(deckId, cards);
        decks.put(deckId, deck);
        return deck;
    }

    public List<String> getAllDecks() {
        return new ArrayList<>(decks.keySet());
    }

    public Deck getDeck(String deckId) {
        return decks.get(deckId);
    }

    public Card dealCard(String deckId) {
        return decks.get(deckId).getCards().remove(0);
    }

    public void returnCard(String deckId, Card card) {
        decks.get(deckId).getCards().add(card);
    }

    public void shuffleDeck(String deckId) {
        Collections.shuffle(decks.get(deckId).getCards());
    }

    public boolean deckExists(String deckId) {
        return decks.containsKey(deckId);
    }

    public void validateDeckId(String deckId) throws InvalidInputException, NotFoundException {
        if (deckId.isEmpty() || deckId.isBlank()) {
            throw new InvalidInputException("Deck id not valid");
        } else if (!deckExists(deckId)) {
            throw new NotFoundException("Deck not found");
        } else if (getDeck(deckId).getCards().isEmpty()) {
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

        if (decks.get(deckId).getCards().contains(card)) {
            throw new InvalidInputException("Card already in deck - cannot return card that is already in deck");
        }
    }
}
