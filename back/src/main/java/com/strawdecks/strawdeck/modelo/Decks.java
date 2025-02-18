package com.strawdecks.strawdeck.modelo;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Decks {
    private int id;
    private int user_id;
    private String name;
    private Timestamp dateofcreation;
    private Boolean activo;
}
