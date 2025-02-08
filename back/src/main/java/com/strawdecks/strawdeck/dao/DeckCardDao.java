package com.strawdecks.strawdeck.dao;

import java.util.List;

public interface DeckCardDao {
    public void addCardsToDeck(Long deckId, String cardId,  int quantity);

    public List<String> getCardsInDeck(Long deckId);

    public void removeCardFromDeck(Long deckId, String cardId);
}
