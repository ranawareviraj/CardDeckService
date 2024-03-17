package com.acme.carddeckservice.service;

import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MockBean(CardDeckService.class)
class CardDeckServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createDeck() {
        CardDeckService cardDeckService = new CardDeckService();
        Deck deck = cardDeckService.createNewDeck();
        assertNotNull(deck);
        assertEquals(52, deck.getCards().size());
    }

    @Test
    void getAllDecks() {
        CardDeckService cardDeckService = new CardDeckService();
        cardDeckService.createNewDeck();
        Map<String, Deck> decks = cardDeckService.getAllDecks();
        assertNotNull(decks);
        assertEquals(1, decks.size());
    }

    @Test
    void getDeck() {
        CardDeckService cardDeckService = new CardDeckService();
        Deck deck = cardDeckService.createNewDeck();
        Deck fetchedDeck = cardDeckService.getDeck(deck.getId());
        assertNotNull(fetchedDeck);
        assertEquals(deck.getId(), fetchedDeck.getId());
    }

    @Test
    void dealCard() {
        CardDeckService cardDeckService = new CardDeckService();
        Deck deck = cardDeckService.createNewDeck();
        Card card = cardDeckService.dealCard(deck.getId());
        assertNotNull(card);
        assertEquals(51, deck.getCards().size());
    }

    @Test
    void returnCard() {
        CardDeckService cardDeckService = new CardDeckService();
        Deck deck = cardDeckService.createNewDeck();
        Card card = cardDeckService.dealCard(deck.getId());
        cardDeckService.returnCard(deck.getId(), card);
        assertEquals(52, deck.getCards().size());
    }

    @Test
    void shuffleDeck() {
        CardDeckService cardDeckService = new CardDeckService();
        Deck deck = cardDeckService.createNewDeck();
        List<Card> originalCards = new CopyOnWriteArrayList<>(deck.getCards());
        cardDeckService.shuffleDeck(deck.getId());
        List<Card> shuffledCards = deck.getCards();
        assertNotEquals(originalCards, shuffledCards);
    }

    @Test
    void deckExists() {
        CardDeckService cardDeckService = new CardDeckService();
        Deck deck = cardDeckService.createNewDeck();
        assertTrue(cardDeckService.deckExists(deck.getId()));
    }

    @Test
    void getAllDeckIds() {
        CardDeckService cardDeckService = new CardDeckService();
        cardDeckService.createNewDeck();
        List<String> deckIds = cardDeckService.getAllDeckIds();
        assertNotNull(deckIds);
        assertEquals(1, deckIds.size());
    }

}