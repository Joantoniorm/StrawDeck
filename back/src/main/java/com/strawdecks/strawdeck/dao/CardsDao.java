package com.strawdecks.strawdeck.dao;

import java.util.List;
import java.util.Optional;

import com.strawdecks.strawdeck.modelo.Cards;

public interface CardsDao {
    public void create (Cards cards);
    public List<Cards> getAll();
    public Optional<Cards> find (String id);
    public void update (Cards cards);
    public void delete (long id);
}
