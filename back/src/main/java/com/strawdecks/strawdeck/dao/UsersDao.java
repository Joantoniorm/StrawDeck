package com.strawdecks.strawdeck.dao;

import java.util.List;
import java.util.Optional;

import com.strawdecks.strawdeck.modelo.Users;

public interface UsersDao {
    public void create (Users users);
    public List<Users> getAll();
    public List <Users> getAllActive();
    public Optional<Users> find (String name);
    public void update (Users users);
    public void delete (int id, boolean activo);
}
