package com.acme.carddeckservice.service;

import com.acme.carddeckservice.Constants;
import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CardDeckService {
    private static final Map<String, Deck> decks = new ConcurrentHashMap<>();
    int count = 0;

    public Deck createDeck() {
        // Initialize deck with 52 cards
        List<Card> cards = new LinkedList<>();
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

    public Deck getDeck(String deckId) {
        return decks.get(deckId);
    }

    public Card dealCard(String deckId) {
        return decks.get(deckId).getCards().remove(0);
    }

    public Card returnLastCard(String deckId) {
        return decks.get(deckId).getCards().remove(decks.get(deckId).getCards().size() - 1);
    }

    public void shuffleDeck(String deckId) {
        Collections.shuffle(decks.get(deckId).getCards());
    }
}
