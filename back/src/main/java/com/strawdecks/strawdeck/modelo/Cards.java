package com.strawdecks.strawdeck.modelo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cards {
    private String id;
    private int cost;
    private String type;
    private String color;
    private String effect;
    private String image;
    private int power;
    private int counter;
    private String name;
}