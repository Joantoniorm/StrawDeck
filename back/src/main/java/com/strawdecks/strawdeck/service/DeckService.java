package com.strawdecks.strawdeck.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.DecksDao;
import com.strawdecks.strawdeck.modelo.Decks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeckService {
    @Autowired
    private DecksDao decksDao;
    public void createDeck(Decks deck){
        deck.setDateofcreation(new Timestamp(System.currentTimeMillis()));
        decksDao.create(deck);
    }
    public List<Decks> getAllDecks(){
        return decksDao.getAll();
    }
    public Optional<Decks> findDeck(int id){
        return decksDao.find(id);
    }
    public List<Decks> getDeckByName(String name){
        return decksDao.getDecksByName(name);
    }
    public List<Decks> getDeckByUser(String nameUser){
        return decksDao.getDeckByUser(nameUser);
    }
    public void deckUpdate(Decks decks){
        decksDao.update(decks);
    }
    public void deckDelete (int id, Boolean activo){
        decksDao.delete(id, activo);
    }
    public List<Decks> getChronoDecks(){
        System.out.println("hola");
        return decksDao.getChronoDecks();
    }
    


}