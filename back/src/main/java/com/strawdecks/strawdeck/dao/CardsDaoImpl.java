package com.strawdecks.strawdeck.dao;
import java.util.List;
import java.util.Optional;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.strawdecks.strawdeck.modelo.Cards;
@Slf4j
@Repository
public class CardsDaoImpl implements CardsDao {
    @Autowired
	 private JdbcTemplate jdbcTemplate;
	
    @Override
    public synchronized void create(Cards cards) { 
        String sqlInsert ="""
                INSERT INTO cards (id, cost, type, color, effect, image, power, counter,name) 
                VALUES (?,?,?,?,?,?,?,?)
                """;
        int rows = jdbcTemplate.update(sqlInsert,
        cards.getId(),cards.getCost(),cards.getType(),
        cards.getColor(),cards.getEffect(),cards.getImage(),
        cards.getPower(),cards.getCounter(),cards.getName());
        if (rows == 0) {
            throw new RuntimeException("Failed to insert card. No rows affected.");
        }
        System.out.println("Card inserted successfully with ID: " + cards.getId());
    }

    @Override
    public List<Cards> getAll() {
        List<Cards> listCards =jdbcTemplate.query(
            "SELECT * FROM cliente",(rs, rowNum) -> new Cards(
                rs.getString("id"), 
                rs.getInt("cost"),
                rs.getString("type"),
                rs.getString("color"),
                rs.getString("effect"),
                rs.getString("image"),
                rs.getInt("power"),
                rs.getInt("counter"),
                rs.getString("name")
            ));
            log.info("Devueltos {} registros", listCards.size());
            return listCards;
    }

    @Override
public Optional<Cards> find(String id) {
    
    Cards card = jdbcTemplate.queryForObject("SELECT * FROM cards WHERE id = ?",(rs, rowNum) -> new Cards(
        rs.getString("id"), 
        rs.getInt("cost"),
        rs.getString("type"),
        rs.getString("color"),
        rs.getString("effect"),
        rs.getString("image"),
        rs.getInt("power"),
        rs.getInt("counter"),
        rs.getString("name")
    ));
    if (card != null) { 
        return Optional.of(card);}
        else{
            log.info("Carta no encontrada.");
            return Optional.empty();
        }

}

    @Override
    public void update(Cards cards) {   
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    
}
