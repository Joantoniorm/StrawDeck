package com.strawdecks.strawdeck.service;

import java.io.InputStream;
import java.util.List;
import com.strawdecks.strawdeck.modelo.Cards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JsonDataLoader implements CommandLineRunner {// Se ejecuta despues del arranque del servidor.
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        loadCardsFromJson("card_data.json");
    }
        
    public void loadCardsFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
            try {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
                if (inputStream == null){
                    System.err.println("No se encuentra el archivo en: "+ filePath);
                    return;
                }
                List<Cards> cartas = objectMapper.readValue(inputStream, new TypeReference<List<Cards>>() {});
                for (Cards carta : cartas){
                    saveCardToDatabase(carta);
                }
                System.out.println("Successfully loaded " + cartas.size() + " cards.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void saveCardToDatabase(Cards carta) {
        String sqlInsert = "INSERT INTO cards (id, cost, type, color, effect, image, power, counter, name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlInsert,
            carta.getId(), carta.getCost(), carta.getType(),
            carta.getColor(), carta.getEffect(), carta.getImage(),
            carta.getPower(), carta.getCounter(), carta.getName());
            System.out.println("Inserted card: " + carta.getName());
        }
}
