package com.strawdecks.strawdeck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.DeckCardDao;
import com.strawdecks.strawdeck.modelo.Cards;
import com.strawdecks.strawdeck.modelo.DeckCard;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeckCardService {
    
    @Autowired
    private DeckCardDao deckCardDao;

    public void addCardsToDeck(Long deckId, List<DeckCard> cards) {
        for (DeckCard card : cards) {
            deckCardDao.addCardsToDeck(deckId, card.getCard_id(), card.getQuantity());
        }
        log.info("Carta {} añadida al deck {}", cards.size(), deckId);
    }

    public List<DeckCard> getCardsInDeck(Long deckId) {
        return deckCardDao.getCardsInDeck(deckId);
    }
    public void removeCardsFromDeck(Long deckId, List<DeckCard> cards){
        for(DeckCard card:cards){
            deckCardDao.removeCardFromDeck(deckId, card.getCard_id());
            log.info("borrada la carta {}",card.getCard_id());
        }
    }
    public void updateCardsInDeck(Long deckId, List<DeckCard> cards){
        for(DeckCard card: cards){
            deckCardDao.updateCardQuantity(deckId, card.getCard_id(), card.getQuantity());
        }
    }
}
