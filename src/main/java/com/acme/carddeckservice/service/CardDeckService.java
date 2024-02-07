package com.acme.carddeckservice.service;

import com.acme.carddeckservice.Constants;
import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CardDeckService {
    private static final Map<Integer, Deck> decks = new ConcurrentHashMap<>();
    int count = 0;

    public Deck createDeck() {
        List<Card> cards = new ArrayList<>();
        // Initialize deck with 52 cards
        for (String suit : Constants.SUITS) {
            for (String rank : Constants.RANKS) {
                cards.add(new Card(suit, rank));
            }
        }
        count++;
        Deck deck = new Deck(String.valueOf(count), cards);
        decks.put(count, deck);
        return deck;
    }

    public static Deck getDeck(String deckId) {
        return decks.get(deckId);
    }

    public static void dealCard(String deckId) {
        //TODO: Implement dealing logic
    }

    public static void returnCard(String deckId) {
        //TODO: Implement returning logic
    }

    public static void shuffleDeck(String deckId) {
        //TODO: Implement shuffling logic
    }
}

