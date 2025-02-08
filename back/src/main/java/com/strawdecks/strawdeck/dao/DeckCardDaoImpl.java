package com.strawdecks.strawdeck.dao;

import java.util.ArrayList;
import java.util.List;

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
            INSERT INTO decks_cards (deck_id, card_id, quantity) 
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE quantity = quantity + ?
            """;
    int rows = jdbcTemplate.update(sqlInsert, deckId, cardId, quantity, quantity);
    if (rows == 0) {
        throw new RuntimeException("Failed to insert card into deck. No rows affected.");
    }
}

    @Override
    public List<String> getCardsInDeck(Long deckId) {
        String sqlSelect = """
                SELECT card_id FROM decks_cards WHERE deck_id = ?
                """;

        return jdbcTemplate.query(sqlSelect, ps -> ps.setLong(1, deckId), rs -> {
            List<String> cardIds = new ArrayList<>();
            while (rs.next()) {
                cardIds.add(rs.getString("card_id"));
            }
            return cardIds;
        });
    }


    @Override
    public void removeCardFromDeck(Long deckId, String cardId) {
        String sqlDelete = """
                DELETE FROM decks_cards WHERE deck_id = ? AND card_id = ?
                """;
        int rows = jdbcTemplate.update(sqlDelete, deckId, cardId);
        if (rows == 0) {
            log.warn("No card with ID {} found in deck with ID {}", cardId, deckId);
        } else {
            log.info("Card with ID {} removed from deck with ID {}", cardId, deckId);
        }
    }

    
}