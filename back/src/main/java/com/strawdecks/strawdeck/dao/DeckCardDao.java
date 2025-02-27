package com.strawdecks.strawdeck.dao;
import com.strawdecks.strawdeck.modelo.Cards;
import com.strawdecks.strawdeck.modelo.DeckCard;

import java.util.List;

public interface DeckCardDao {
    public void addCardsToDeck(Long deckId, String cardId,  int quantity);

    public List<DeckCard> getCardsInDeck(Long deckId);

    public void removeCardFromDeck(Long deckId, String cardId);
    public void updateCardQuantity(Long deckId, String cardId, int quantity);
}
