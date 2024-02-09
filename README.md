# CardDeckService
### Author: [Viraj Ranaware](https://www.linkedin.com/in/ranawareviraj)

## Overview

The CardDeckService is proof of concept web service which provides essential functionality for managing decks of cards in online card games to be added to its products line by ACME Corp. It provides a set of APIs to create, manage, shuffle, deal, and return cards to custom decks.

## Features

- **Deck Management:** Create and manage multiple decks, each associated with a unique identifier.
- **Shuffling:** Shuffle the cards within a deck for a randomized playing experience.
- **Dealing:** Deal cards from the top of the deck.
- **Returning:** Return cards to the bottom of the deck.
- **Multiple Games:** It supports for concurrent games, each with its own isolated deck.

### API Documentation
- The service specification is documented using OpenAPI 3.0.0. The API documentation is available at [CardDeckService Specification](http://161.35.189.144:3000/swagger-ui/index.html).

## Getting Started

Below are the steps to build and run the application.

### Prerequisites

- Java (JDK 17 or higher)
- Maven (Alternatively, you can use the Maven wrapper included in the project)

### Build and Run

1. Clone the repository locally:

    ```bash
    git clone https://github.com/your-username/CardDeckService.git
    ```

2. Navigate to the project directory:

    ```bash
    cd CardDeckService/card-deck-service
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/card-deck-service-1.0.0.jar
    ```

Alternatively, you can run the application using the jar available in the resources' directory.
 ```bash
    java -jar card-deck-service-1.0.0.jar
 ```

### Server Information

- **Base URL:**
   - If you are running app locally: [http://localhost:8080](http://localhost:8080)
   - Live server: [http://161.35.189.144:3000](http://161.35.189.144:3000)
## API Endpoints

### 1. Create a New Deck
- **Endpoint:** `GET` `/api/cards-service/decks/new`

### 2. Fetch All Decks
- **Endpoint:** `GET` `/api/cards-service/decks`

### 3. Fetch a Deck
- **Endpoint:** `GET` `/api/cards-service/decks/{deckId}`

### 4. Shuffle a Deck
- **Endpoint:** `GET` `/api/cards-service/decks/{deckId}/shuffle`

### 5. Deal a Card
- **Endpoint:** `GET` `/api/cards-service/decks/{deckId}/deal`

### 6. Return a Card
- **Endpoint:** `POST` `/api/cards-service/decks/{deckId}/cards`


- All endpoints accept an optional header X-Request-Id, which can be used to trace the request. The value of the header is a unique identifier for the request. If not provided, the service will generate a unique identifier for the request. It is used for logging and tracing purposes.
- For more details, please refer to the [CardDeckService Specification](http://161.35.189.144:3000/swagger-ui/index.html).

## Notes and Assumptions
- The service assumes that the deck is a standard 52-card deck composed of 
  - Suits: [ Hearts, Diamonds, Clubs, Spades ] 
  - Ranks for each suite: [ 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace]
- The service supports concurrent games. Each game has its own isolated deck, and it can be managed using the deckId.
- The service does not persist the decks. It is an in-memory service, and the decks are lost when the service is restarted.
- The service does not support multiple decks within a game. It only supports a single deck per game.
- The service does not support custom decks. It only supports standard 52-card decks. To support custom decks, the service needs code/configuration changes.
- The service is not complete and has room for improvement. It is a basic implementation to demonstrate the essential functionality of managing decks of cards.
- The service does not have any security features. It is an open service, and anyone can access the APIs.
- This is a basic implementation, and it does not have extensive error handling, though it supports some input validation, as well as validations on resource requested. Not all validations are documented in the API specification. The specification only has the happy path scenarios mostly with few error cases.
