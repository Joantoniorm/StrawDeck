package com.strawdecks.strawdeck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.CardsDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CardService {
    @Autowired
    private CardsDao cardsDao;

    
}
