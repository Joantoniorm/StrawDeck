package com.strawdecks.strawdeck.dao;

import java.util.ArrayList;
import java.util.List;

import com.strawdecks.strawdeck.modelo.Cards;
import com.strawdecks.strawdeck.modelo.DeckCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DeckCardDaoImpl implements DeckCardDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
public void addCardsToDeck(Long deckId, String cardId, int quantity) {
    String sqlInsert = """
            INSERT INTO deck_cards (deck_id, card_id, quantity) 
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE quantity = quantity + ?
            """;
    int rows = jdbcTemplate.update(sqlInsert, deckId, cardId, quantity, quantity);
    if (rows == 0) {
        throw new RuntimeException("Failed to insert card into deck. No rows affected.");
    }
}

@Override
public List<DeckCard> getCardsInDeck(Long deckId) {
    String sqlSelect = """
        SELECT card_id, quantity
        FROM deck_cards
        WHERE deck_id = ?
    """;

    return jdbcTemplate.query(sqlSelect, ps -> {
        int idx = 1;
        ps.setLong(idx, deckId); 
    }, (rs, rowNum) -> {
        DeckCard deckCard = new DeckCard();
        deckCard.setCard_id(rs.getString("card_id"));
        deckCard.setQuantity(rs.getInt("quantity"));
        return deckCard;
    });
}


    @Override
    public void removeCardFromDeck(Long deckId, String cardId) {
        String sqlDelete = """
                DELETE FROM deck_cards WHERE deck_id = ? AND card_id = ?
                """;
        int rows = jdbcTemplate.update(sqlDelete, deckId, cardId);
        if (rows == 0) {
            log.warn("No se ha eliminado ninguna carta con id {} que pertenezca al deck {}", cardId, deckId);
        } else {
            log.info("Se ha eliminado una carta con id {} que pertenezca al deck {}", cardId, deckId);
        }
    }
    @Override
    public void updateCardQuantity(Long deckId, String cardId, int quantity){
        String sqlUpdate = """
                UPDATe deck_cards
                SET quantity = ?
                WHERE deck_id = ? AND card_id = ?
                """;
                int rows = jdbcTemplate.update(sqlUpdate,quantity, deckId, cardId);
                if (rows==0) {
                    log.warn("No se ha actualizado ninguna carta con id {} que pertenezca al deck {}", cardId, deckId);
                } else{
                    log.warn("Se ha actualizado una carta con id {} que pertenezca al deck {}", cardId, deckId);
                }
    }

    
}