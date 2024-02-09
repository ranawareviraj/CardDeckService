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

- **Endpoint:** `/api/cards-service/decks/{deckId}/cards`
- **HTTP Method:** POST
- **Summary:** Return a card
- **Description:** Returns a card to a deck specified by the provided `deckId`.

  #### Request

  | Parameter      | Type      | Description                          |
    | -------------- | --------- | ------------------------------------ |
  | `deckId`       | `string`  | **Required**. Identifier of the deck. |
  | `X-Request-ID` | `string`  | Optional. Unique identifier for the request. |

  #### Request Body

   - Content Type: `application/json`

  | Field | Type   | Description |
    | ----- | ------ | ----------- |
  | `suit` | `string` | Suit of the card. |
  | `rank` | `string` | Rank of the card. |

  #### Responses

  | HTTP Code | Description                 |
    | --------- | --------------------------- |
  | `200`     | Successfully returned a card |
  | `404`     | Deck not found               |

### 2. Fetch All Decks

- **Endpoint:** `/api/cards-service/decks`
- **HTTP Method:** GET
- **Summary:** Fetch all decks
- **Description:** Fetches all deckIDs of all available decks.

  #### Request

  | Parameter      | Type      | Description                          |
    | -------------- | --------- | ------------------------------------ |
  | `X-Request-ID` | `string`  | Optional. Unique identifier for the request. |

  #### Responses

  | HTTP Code | Description                           |
    | --------- | ------------------------------------- |
  | `200`     | Successfully fetched all deckIDs      |

### 3. Fetch a Deck

- **Endpoint:** `/api/cards-service/decks/{deckId}`
- **HTTP Method:** GET
- **Summary:** Fetch a deck
- **Description:** Fetches a deck of cards by deckID.

  #### Request

  | Parameter      | Type      | Description                          |
    | -------------- | --------- | ------------------------------------ |
  | `deckId`       | `string`  | **Required**. Identifier of the deck. |
  | `X-Request-ID` | `string`  | Optional. Unique identifier for the request. |

  #### Responses

  | HTTP Code | Description                    |
    | --------- | ------------------------------ |
  | `200`     | Successfully fetched the deck  |

### 4. Shuffle a Deck

- **Endpoint:** `/api/cards-service/decks/{deckId}/shuffle`
- **HTTP Method:** GET
- **Summary:** Shuffle a deck
- **Description:** Shuffles a deck of cards by deckID.

  #### Request

  | Parameter      | Type      | Description                          |
    | -------------- | --------- | ------------------------------------ |
  | `deckId`       | `string`  | **Required**. Identifier of the deck. |
  | `X-Request-ID` | `string`  | Optional. Unique identifier for the request. |

  #### Responses

  | HTTP Code | Description                  |
    | --------- | ---------------------------- |
  | `200`     | Successfully shuffled the deck|
  | `404`     | Deck not found                |

### 5. Deal a Card

- **Endpoint:** `/api/cards-service/decks/{deckId}/deal`
- **HTTP Method:** GET
- **Summary:** Deal a card
- **Description:** Deals a card from a deck by deckID.

  #### Request

  | Parameter      | Type      | Description                          |
    | -------------- | --------- | ------------------------------------ |
  | `deckId`       | `string`  | **Required**. Identifier of the deck. |
  | `X-Request-ID` | `string`  | Optional. Unique identifier for the request. |

  #### Responses

  | HTTP Code | Description                 |
    | --------- | --------------------------- |
  | `200`     | Successfully dealt a card   |

### 6. Create a New Deck

- **Endpoint:** `/api/cards-service/decks/new`
- **HTTP Method:** GET
- **Summary:** Create a new deck
- **Description:** Creates a new deck of cards and returns the deckID.

  #### Request

  | Parameter      | Type      | Description                          |
    | -------------- | --------- | ------------------------------------ |
  | `X-Request-ID` | `string`  | Optional. Unique identifier for the request. |

  #### Responses

  | HTTP Code | Description                   |
    | --------- | ----------------------------- |
  | `201`     | Successfully created a new deck|

## Schema Definitions

### Card Schema

```json
{
  "type": "object",
  "properties": {
    "suit": {
      "type": "string"
    },
    "rank": {
      "type": "string"
    }
  }
}
