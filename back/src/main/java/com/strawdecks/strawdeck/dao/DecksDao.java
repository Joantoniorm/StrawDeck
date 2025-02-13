package com.strawdecks.strawdeck.dao;

import java.util.List;
import java.util.Optional;

import com.strawdecks.strawdeck.modelo.Decks;
public interface DecksDao {
    public void create (Decks decks);
    public List<Decks> getAll();
    public Optional<Decks> find (String name);
    public Optional<Decks> getDeckByUser(int user_id);
    public void update (Decks decks);
    public void delete (int id);
}
