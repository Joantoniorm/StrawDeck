package com.strawdecks.strawdeck.dao;

import java.util.List;
import java.util.Optional;

import com.strawdecks.strawdeck.modelo.Decks;
public interface DecksDao {
    public void create (Decks decks);
    public List<Decks> getAll();
    public Optional<Decks> find (int id);
    public List<Decks> getDecksByName(String name);
    public List<Decks> getDeckByUser(String nameUser);
    public void update (Decks decks);
    public void delete (int id, Boolean activo);
}
