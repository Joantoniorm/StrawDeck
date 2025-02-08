package com.strawdecks.strawdeck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strawdecks.strawdeck.modelo.deck_cards;
import com.strawdecks.strawdeck.service.DeckCardService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/decks")
public class DeckCardController {
    @Autowired
    private DeckCardService deckCardService;

    @PostMapping("/{deckId}/addCards")
    public String addCardsToDeck(@PathVariable Long deckId, @RequestBody List<deck_cards> cards) {
    deckCardService.addCardsToDeck(deckId, cards);
    return "Added cards to deck " + deckId;
}



    @GetMapping("/{deckId}/cards")
    public List<String> getCardsInDeck(@PathVariable Long deckId) {
        return deckCardService.getCardsInDeck(deckId);
    }

    @DeleteMapping("/{deckId}/removeCard/{cardId}")
    public String removeCardFromDeck(@PathVariable Long deckId, @PathVariable String cardId) {
        deckCardService.removeCardFromDeck(deckId, cardId);
        return "Card " + cardId + " removed from deck " + deckId;
    }
}

