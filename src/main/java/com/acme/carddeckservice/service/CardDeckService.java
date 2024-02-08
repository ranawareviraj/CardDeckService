package com.acme.carddeckservice.service;

import com.acme.carddeckservice.utils.Constants;
import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The CardDeckService class is a service class that provides methods
 * to create, get, shuffle, deal and return cards to a deck.
 * It also provides methods to get all decks and deck IDs.
 *
 * @author Viraj Ranaware
 */
@Service
public class CardDeckService {
    private static final Map<String, Deck> decks = new ConcurrentHashMap<>();
    long count = 0;

    public Deck createDeck() {
        // Initialize deck with 52 cards
        List<Card> cards = new CopyOnWriteArrayList<>();
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

    public Map<String, Deck> getAllDecks() {
        return decks;
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

    public List<String> getAllDeckIds() {
        return new ArrayList<>(decks.keySet());
    }
}
