package com.strawdecks.strawdeck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.DeckCardDao;
import com.strawdecks.strawdeck.modelo.deck_cards;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeckCardService {
    
    @Autowired
    private DeckCardDao deckCardDao;
    public void addCardsToDeck(Long deckId, List<deck_cards> cards) {
    for (deck_cards card : cards) {
        deckCardDao.addCardsToDeck(deckId, card.getCard_id(), card.getQuantity());
    }
    log.info("Added {} cards to deck {}", cards.size(), deckId);
}


     public List<String> getCardsInDeck(Long deckId) {
        return deckCardDao.getCardsInDeck(deckId);
    }
    public void removeCardFromDeck(Long deckId, String cardId) {
        deckCardDao.removeCardFromDeck(deckId, cardId);
        log.info("Removed card {} from deck {}", cardId, deckId);
    }
}
