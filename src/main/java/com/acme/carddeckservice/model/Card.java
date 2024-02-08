package com.acme.carddeckservice.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

/**
 * The Card class represents a playing card, which has a suit and a rank.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Card {
    private String suit;
    private String rank;
}
