package com.strawdecks.strawdeck.modelo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Cards {
    private String id;
    private int cost;
    private String type;
    private String color;
    private String effect;
    @JsonProperty ("image_url")
    private String image;
    private int power;
    private int counter;
    private String name;
    
}