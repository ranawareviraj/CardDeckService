package com.acme.carddeckservice.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Card {
    private String suit;
    private String rank;
}

