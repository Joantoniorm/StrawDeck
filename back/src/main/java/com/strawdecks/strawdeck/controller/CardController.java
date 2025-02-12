package com.strawdecks.strawdeck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strawdecks.strawdeck.service.CardService;
import com.strawdecks.strawdeck.service.JsonDataLoader;
import org.springframework.web.bind.annotation.PostMapping;
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
}