package com.acme.carddeckservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The Deck class represents a deck of playing cards.
 * It has a unique ID and a list of cards that are currently in deck.
 *
 * @author Viraj Ranaware
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Deck {
    private String id;
    private List<Card> cards;
}
