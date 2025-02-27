package com.strawdecks.strawdeck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strawdecks.strawdeck.modelo.Cards;
import com.strawdecks.strawdeck.modelo.Decks;
import com.strawdecks.strawdeck.modelo.DeckCard;
import com.strawdecks.strawdeck.service.DeckCardService;
import com.strawdecks.strawdeck.service.DeckService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/decks")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @PostMapping("/create")
    public ResponseEntity<String> createDeck(@RequestBody Decks decks) {
        deckService.createDeck(decks);
        return ResponseEntity.ok("Carta creada exitosamente");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDeck(@RequestBody Decks decks) {
        deckService.deckUpdate(decks);
        return ResponseEntity.ok("Carta actualizada exitosamente");
    }
    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteDeck(@RequestParam int id){
        deckService.deckDelete(id, false);
        return ResponseEntity.ok("Deck eliminado con exito");
    }
    @GetMapping("/all")
    public ResponseEntity<List<Decks>> getAllDecks() {
        return ResponseEntity.ok(deckService.getAllDecks());
    }

    @GetMapping("/all/user/{id}")
    public ResponseEntity<List<Decks>> getDecksByUser(@PathVariable String nameUser) {
        List<Decks> decks = deckService.getDeckByUser(nameUser);
        return ResponseEntity.ok(decks);
    }
    @GetMapping("/all/deck/{name}")
    public ResponseEntity<List<Decks>> getDecksByName(@RequestParam String nameDeck) {
        List<Decks> decks = deckService.getDeckByName(nameDeck);
        return ResponseEntity.ok(decks);
    }

    @Autowired
    private DeckCardService deckCardService;

    @PostMapping("/{deckId}/addCards")
    public ResponseEntity<?> addCardsToDeck(@PathVariable Long deckId, @RequestBody List<DeckCard> cards) {
        deckCardService.addCardsToDeck(deckId, cards);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/{deckId}/cards")
    public ResponseEntity<List<DeckCard>> getCardsInDeck(@PathVariable Long deckId) {
        List<DeckCard> cards = deckCardService.getCardsInDeck(deckId);
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/{deckId}/deleteCards")
    public ResponseEntity<?> deleteCardsFromDeck(@PathVariable Long deckId, @RequestBody List<DeckCard> cards) {
        deckCardService.removeCardsFromDeck(deckId, cards);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{deckId}/updateCards")
    public ResponseEntity<?> updateCardsInDeck(@PathVariable Long deckId, @RequestBody List<DeckCard> cards) {
        deckCardService.updateCardsInDeck(deckId, cards);
        return ResponseEntity.ok().build();
    }
    
    
}
