package com.acme.carddeckservice.utils;

import java.util.Set;

/**
 * The Constants class contains the constants used in the application.
 */
public class Constants {
    public static final Set<String> SUITS = Set.of("Hearts", "Diamonds", "Clubs", "Spades");
    public static final Set<String> RANKS = Set.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace");
    public static final String INVALID_REQUEST = "INVALID_REQUEST";
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
}
