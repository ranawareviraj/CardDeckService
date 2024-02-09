# CardDeckService

## Overview

The CardDeckService is web service which provides essential functionality for managing decks of cards in online card games. It provides a set of APIs to create, manage, shuffle, deal, and return cards to custom decks.

## Features

- **Deck Management:** Create and manage multiple decks, each associated with a unique identifier.
- **Shuffling:** Shuffle the cards within a deck for a randomized playing experience.
- **Dealing:** Deal cards from the top of the deck.
- **Returning:** Return cards to the bottom of the deck.
- **Multiple Games:** It supports for concurrent games, each with its own isolated deck.

## Getting Started

### Prerequisites

- Java (JDK 17 or higher)
- Maven

### Build and Run

1. Clone the repository locally:

    ```bash
    git clone https://github.com/your-username/CardDeckService.git
    ```

2. Navigate to the project directory:

    ```bash
    cd CardDeckService
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/card-deck-service.jar
    ```

## API Usage

# CardDeckService

## Overview

The CardDeckService is web service which provides essential functionality for managing decks of cards in online card games. It provides a set of APIs to create, manage, shuffle, deal, and return cards to custom decks.

## Features

- **Deck Management:** Create and manage multiple decks, each associated with a unique identifier.
- **Shuffling:** Shuffle the cards within a deck for a randomized playing experience.
- **Dealing:** Deal cards from the top of the deck.
- **Returning:** Return cards to the bottom of the deck.
- **Multiple Games:** It supports for concurrent games, each with its own isolated deck.

## Getting Started

### Prerequisites

- Java (JDK 17 or higher)
- Maven

### Build and Run

1. Clone the repository locally:

    ```bash
    git clone https://github.com/your-username/CardDeckService.git
    ```

2. Navigate to the project directory:

    ```bash
    cd CardDeckService
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/card-deck-service.jar
    ```

## API Usage

# CardDeckService API

This documentation outlines the CardDeckService API, which facilitates the management of decks in online card games. The service provides functionalities for creating, retrieving, shuffling, dealing, and returning cards to and from decks.

## Server Information

- **Base URL:** [http://localhost:8080](http://localhost:8080)
- **Description:** Generated server URL

## API Endpoints

### 1. Return a Card
- **Endpoint:** `POST` `/api/cards-service/decks/{deckId}/cards`
- **HTTP Method:** 

### 2. Fetch All Decks
- **Endpoint:** `GET` `/api/cards-service/decks`

### 3. Fetch a Deck
- **Endpoint:** `GET` `/api/cards-service/decks/{deckId}`

### 4. Shuffle a Deck
- **Endpoint:** `GET` `/api/cards-service/decks/{deckId}/shuffle`

### 5. Deal a Card
- **Endpoint:** `GET` `/api/cards-service/decks/{deckId}/deal`

### 6. Create a New Deck 
- **Endpoint:** `GET` `/api/cards-service/decks/new`

