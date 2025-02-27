package com.strawdecks.strawdeck.dao;
import java.util.List;
import java.util.Optional;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

         String imageUrl = cards.getImage();
         String baseId = extractBaseIdFromImageUrl(imageUrl);
         System.err.println("hOLA MUNDO "+ baseId);
     
         String checkIfExistsQuery = "SELECT COUNT(*) FROM cards WHERE id = ?";
         Integer count = jdbcTemplate.queryForObject(checkIfExistsQuery, Integer.class, baseId);
     
         if (count != null && count > 0) {

             System.out.println("La carta con ID " + baseId + " ya existe. Actualizando el registro...");
             String sqlUpdate = """
                 UPDATE cards
                 SET cost = ?, type = ?, color = ?, effect = ?, image = ?, power = ?, counter = ?, name = ?
                 WHERE id = ?
             """;
     
             int rowsUpdated = jdbcTemplate.update(sqlUpdate,
                 cards.getCost(), cards.getType(), cards.getColor(), cards.getEffect(),
                 cards.getImage(), cards.getPower(), cards.getCounter(), cards.getName(), baseId);
     
             if (rowsUpdated > 0) {
                 System.out.println("Carta con ID " + baseId + " actualizada con éxito.");
             } else {
                 System.err.println("No se pudo actualizar la carta con id: " + baseId);
             }
         } else {

             String sqlInsert = """
                 INSERT INTO cards (id, cost, type, color, effect, image, power, counter, name)
                 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
             """;
     
             try {
                 int rows = jdbcTemplate.update(sqlInsert,
                     baseId, cards.getCost(), cards.getType(),
                     cards.getColor(), cards.getEffect(), cards.getImage(),
                     cards.getPower(), cards.getCounter(), cards.getName());
     
                 if (rows > 0) {
                     System.out.println("Carta insertada con éxito con id: " + baseId);
                 } else {
                     throw new RuntimeException("No se pudo insertar la carta.");
                 }
             } catch (DuplicateKeyException e) {
                 System.err.println("Error de id");
             }
         }
     }
     
   // Extraemos la parte de la URL después de la última barra "/" y antes de .png
     private String extractBaseIdFromImageUrl(String imageUrl) {
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        String baseId = imageName.substring(0, imageName.lastIndexOf("."));
        return baseId;
    }
    
    

    @Override
    public List<Cards> getAll() {
        List<Cards> listCards =jdbcTemplate.query(
            "SELECT * FROM cards",(rs, rowNum) -> new Cards(
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
            log.info("Devueltos"+ listCards.size()+ "registros");
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
        ),id);
        if (card != null) { 
            return Optional.of(card);}
            else{
                System.err.println("Carta no encontrada.");
                return Optional.empty();
            }

    }

    @Override
    public void update(Cards cards) {
        String sqlUpdate = """
            UPDATE cards
            SET cost = ?, type = ?, color = ?, effect = ?, image = ?, power = ?, counter = ?, name = ?
            WHERE id = ?
        """;
        
        int rowsUpdated = jdbcTemplate.update(sqlUpdate,
            cards.getCost(), cards.getType(), cards.getColor(), cards.getEffect(),
            cards.getImage(), cards.getPower(), cards.getCounter(), cards.getName(), cards.getId());
        
        if (rowsUpdated > 0) {
            System.out.println("Carta con ID" + cards.getId() +"actualizada con éxito.");
        } else {
            System.err.println("No se pudo actualizar la carta con ID:"+cards.getId());
        }
    }

    @Override
    public void delete(long id) {
        String sqlDelete = "DELETE FROM cards WHERE id = ?";
        
        int rowsDeleted = jdbcTemplate.update(sqlDelete, id);
        
        if (rowsDeleted > 0) {
           System.out.println("Carta con ID" +id+ "eliminada con éxito.");
        } else {
            System.err.println("No se encontró ninguna carta con ID:" +id+  "para eliminar.");
        }
    }

    
}
