package com.acme.carddeckservice.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Deck {
    private String id;
    private List<Card> cards;
}

