package com.strawdecks.strawdeck.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Users {
    private int id;
    private String name;
    private String password;
    private String gmail;
    private Boolean activo;
}
