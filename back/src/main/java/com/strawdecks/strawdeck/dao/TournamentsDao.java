package com.strawdecks.strawdeck.dao;
import java.util.List;
import java.util.Optional;
import com.strawdecks.strawdeck.modelo.Tournaments;

public interface TournamentsDao {
    public void create (Tournaments tournaments);
    public List<Tournaments> getAll();
    public List<Tournaments> getAllActive();
    public Optional<Tournaments> find (int id);
    public void update (Tournaments tournaments);
    public void delete (int id, boolean activo);
}
