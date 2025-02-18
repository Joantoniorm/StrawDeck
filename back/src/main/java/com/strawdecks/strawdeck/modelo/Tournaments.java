package com.strawdecks.strawdeck.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tournaments {
    private int id;
    private int user_id;
    private String name;
    private Boolean activo;
}
