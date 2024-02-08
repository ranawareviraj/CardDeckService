package com.acme.carddeckservice.controller;

import com.acme.carddeckservice.model.Card;
import com.acme.carddeckservice.model.Deck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "CardDeckService", description = """
        The CardDeckService provides essential functionality for managing decks of cards in online card games.
        This service enables the creation, retrieval, shuffling, dealing, and returning of cards to and from decks.
        """)
public interface CardDeckAPI {
    @Operation(
            summary = "Fetch all decks",
            description = "Fetches all deckIDs of all available decks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all deckIDs"),

    })
    ResponseEntity<List<String>> getAllDeckIds(String requestId);

    @Operation(
            summary = "Create a new deck",
            description = "Creates a new deck of cards and returns the deckID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new deck"),
    })
    ResponseEntity<Deck> createDeck(String requestId);

    @Operation(
            summary = "Fetch a deck",
            description = "Fetches a deck of cards by deckID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the deck"),
    })
    ResponseEntity<Deck> getDeck(String requestId, String deckId);

    @Operation(
            summary = "Shuffle a deck",
            description = "Shuffles a deck of cards by deckID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully shuffled the deck"),
            @ApiResponse(responseCode = "404", description = "Deck not found"),
    })
    ResponseEntity<Void> shuffleDeck(String requestId, String deckId);

    @Operation(
            summary = "Deal a card",
            description = "Deals a card from a deck by deckID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully dealt a card"),
    })
    ResponseEntity<Card> dealCard(String requestId, String deckId);

    @Operation(
            summary = "Return a card",
            description = "Returns a card to a deck by deckID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a card"),
            @ApiResponse(responseCode = "404", description = "Deck not found"),
    })
    ResponseEntity<Void> returnCard(String deckId, Card card, String requestId);

}