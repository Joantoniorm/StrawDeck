package com.strawdecks.strawdeck.dao;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.strawdecks.strawdeck.modelo.Decks;
import com.strawdecks.strawdeck.modelo.Users;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DecksDaoImpl implements DecksDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
public void create(Decks decks) {

    // SQL con el parámetro 'id' omitido porque es autoincremental
    String sqlInsert = """
            INSERT INTO decks (user_id, name, dateofcreation, activo) VALUES (?,?,?,?)
            """;
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] {"id"});
        int idx = 1;
        ps.setInt(idx++, decks.getUser_id());
        ps.setString(idx++, decks.getName());
        ps.setTimestamp(idx++, decks.getDateofcreation());  
        ps.setBoolean(idx++, true);
        return ps;
    }, keyHolder);
    
    decks.setId(keyHolder.getKey().intValue());
}

    @Override
    public List<Decks> getAll() {
        List<Decks> listDecks = jdbcTemplate.query("Select * from decks WHERE activo=1", (rs, rowNum) -> new Decks(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getTimestamp("dateofcreation"),
            rs.getBoolean("activo"))
            );
            return listDecks;
    }

    @Override
    public Optional<Decks> find(int id) {
        Decks deck = jdbcTemplate.queryForObject("Select * FROM decks WHERE id = ?", (rs, rowNum)->
        new Decks(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getTimestamp("dateofcreation"),
            rs.getBoolean("activo")),id
            );
            
            if (deck!=null) {
                return Optional.of(deck);
            }else{
                System.out.println("No existe deck");
                return Optional.empty();
            }
    }

    @Override
    public List<Decks> getDeckByUser(String nameUser) {
       String UserId = "SELECT id FROM users WHERE name = ?";
       Integer userId;
        //El queryForObject no devuelve null, comprobamos sí lanza la excepción.
        try {
            userId =jdbcTemplate.queryForObject(UserId, Integer.class, nameUser);
        }catch(EmptyResultDataAccessException e){
            return Collections.emptyList();
        }

        String sqlDecks = "SELECT * FROM decks WHERE user_id = ?";
        return jdbcTemplate.query(sqlDecks, (rs, rowNum) -> new Decks(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getTimestamp("dateofcreation"),
            rs.getBoolean("activo")
        ), userId);
    }

    @Override
    public void update(Decks decks) {
        int rows = jdbcTemplate.update("""
                UPDATE decks SET
                name = ?
                WHERE id =?
                """, decks.getName(), decks.getId()
                );
    }

    @Override
    public void delete(int id,Boolean activo) {
      Integer count;

      try{
        count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM decks WHERE id = ?", Integer.class, id);
      }catch(EmptyResultDataAccessException e){
        count= 0;
      }
      if(count!=null && count>0){
        int rows = jdbcTemplate.update("UPDATE decks SET activo = ? WHERE id = ?", activo, id);
        if (rows > 0) {
            System.out.println("Deck con Id"+ id + " actualizado");
        }else{
            System.out.println("No se pudo activar o desactivar el deck con id " + id);
        }
      }else{
        System.out.println("El deck con Id "+ id + "no existe");
      }
    }

    @Override
    public List<Decks> getDecksByName(String name) {
        List<Decks> listDecks = jdbcTemplate.query("Select * from decks WHERE name LIKE ?", (rs, rowNum) -> new Decks(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getTimestamp("dateofcreation"),
            rs.getBoolean("activo")), 
            "%" + name + "%"
            );
            return listDecks;
    }

    @Override
    public List<Decks> getChronoDecks(){

        List<Decks> listDecks = jdbcTemplate.query("SELECT * FROM decks WHERE activo = 1 ORDER BY dateofcreation DESC LIMIT 15", (rs, rowNum) -> new Decks(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getTimestamp("dateofcreation"),
            rs.getBoolean("activo"))
            );
            return listDecks;
    }

    
}

