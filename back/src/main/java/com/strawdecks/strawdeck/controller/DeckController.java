package com.strawdecks.strawdeck.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
    //Con token
    @PostMapping("/create")
    public ResponseEntity<Integer> createDeck(@RequestBody Decks decks) {
        decks.setDateofcreation(new Timestamp(System.currentTimeMillis()));
        deckService.createDeck(decks);
        return ResponseEntity.ok(decks.getId());
    }
//Con token
    @PutMapping("/update")
    public ResponseEntity<String> updateDeck(@RequestBody Decks decks) {
        deckService.deckUpdate(decks);
        return ResponseEntity.ok("Carta actualizada exitosamente");
    }
    //Con token
    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteDeck(@PathVariable int id){
        deckService.deckDelete(id, false);
        return ResponseEntity.ok("Deck eliminado con exito");
    }
    //Sin token
    @GetMapping("/all")
    public ResponseEntity<List<Decks>> getAllDecks() {
        return ResponseEntity.ok(deckService.getAllDecks());
    }
    //Sin token
    @GetMapping("/{id}")
    public ResponseEntity<Decks> getDeckById(@PathVariable int id) {
        Optional<Decks> deck = deckService.findDeck(id);
        return deck.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    //Sin token
    @GetMapping("/lastDecks")
    public ResponseEntity<List<Decks>> getNewDecks() {
        return ResponseEntity.ok(deckService.getChronoDecks());
    }
    
    
    @GetMapping("/all/user/{id}")
    public ResponseEntity<List<Decks>> getDecksByUser(@PathVariable String nameUser) {
        List<Decks> decks = deckService.getDeckByUser(nameUser);
        return ResponseEntity.ok(decks);
    }
    //Sin token
    @GetMapping("/all/deck/{name}")
    public ResponseEntity<List<Decks>> getDecksByName(@RequestParam String nameDeck) {
        List<Decks> decks = deckService.getDeckByName(nameDeck);
        return ResponseEntity.ok(decks);
    }

    @Autowired
    private DeckCardService deckCardService;
    //Con token
    @PostMapping("/{deckId}/addCards")
    public ResponseEntity<?> addCardsToDeck(@PathVariable Long deckId, @RequestBody List<DeckCard> cards) {
        deckCardService.addCardsToDeck(deckId, cards);
        return ResponseEntity.ok().build();
    }


    //Sin token
    @GetMapping("/{deckId}/cards")
    public ResponseEntity<List<DeckCard>> getCardsInDeck(@PathVariable Long deckId) {
        List<DeckCard> cards = deckCardService.getCardsInDeck(deckId);
        return ResponseEntity.ok(cards);
    }
//Con token
    @PostMapping("/{deckId}/deleteCards")
    public ResponseEntity<?> deleteCardsFromDeck(@PathVariable Long deckId, @RequestBody List<DeckCard> cards) {
        deckCardService.removeCardsFromDeck(deckId, cards);
        return ResponseEntity.ok().build();
    }
    //Con token
    @PostMapping("/{deckId}/updateCards")
    public ResponseEntity<?> updateCardsInDeck(@PathVariable Long deckId, @RequestBody List<DeckCard> cards) {
        deckCardService.updateCardsInDeck(deckId, cards);
        return ResponseEntity.ok().build();
    }
    
    
}
