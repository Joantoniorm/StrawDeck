package com.strawdecks.strawdeck.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeckCard {
    private int deck_id;
    private String card_id;
    private int quantity;
}
