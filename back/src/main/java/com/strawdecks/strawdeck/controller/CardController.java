package com.strawdecks.strawdeck.controller;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strawdecks.strawdeck.modelo.Cards;
import com.strawdecks.strawdeck.service.CardService;
import com.strawdecks.strawdeck.service.JsonDataLoader;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/cards")
public class CardController{

    @Autowired
    private CardService cardService;
    @Autowired 
    private JsonDataLoader jsonDataLoader;
    @PostMapping("/load")
    public String loadCardsFromJson(@RequestParam String filePath) {
        jsonDataLoader.loadCardsFromJson(filePath);
        return "Cartas cargadas con exito desde: "+ filePath;  
    }
     @PostMapping("/create")
    public ResponseEntity<String> createCard(@RequestBody Cards card) {
        cardService.createCard(card);
        return ResponseEntity.ok("Carta creada exitosamente");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cards>> getAllCards() {

        return ResponseEntity.ok(cardService.getAllCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cards> getCardById(@PathVariable String id) {

        Optional<Cards> card = cardService.getCardById(id);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCard(@RequestBody Cards card) {
        cardService.updateCards(card);
        return ResponseEntity.ok("Carta actualizada exitosamente");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok("Carta eliminada exitosamente");
    }
    
}