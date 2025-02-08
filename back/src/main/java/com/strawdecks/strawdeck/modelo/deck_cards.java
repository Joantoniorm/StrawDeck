package com.strawdecks.strawdeck.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class deck_cards {
    private int deck_id;
    private String card_id;
    private int quantity;
}
