package com.strawdecks.strawdeck.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.CardsDao;
import com.strawdecks.strawdeck.modelo.Cards;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CardService {
    @Autowired
    private CardsDao cardsDao;
    public void createCard(Cards card){
        cardsDao.create(card);
    }
    public List<Cards> getAllCards(){
        return cardsDao.getAll();
    }
    public Optional<Cards> getCardById(String id){
        return cardsDao.find(id);
    }
    public void updateCards(Cards card){
        cardsDao.update(card);
    }
    public void deleteCard(long id) {
        cardsDao.delete(id);
    }
}
