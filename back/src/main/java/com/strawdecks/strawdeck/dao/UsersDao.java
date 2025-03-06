package com.strawdecks.strawdeck.dao;

import java.util.List;
import java.util.Optional;

import com.strawdecks.strawdeck.modelo.Users;

public interface UsersDao {
    public void create (Users users);
    //Incluir en el service cuando se implemente un sistema de permisos
    public List<Users> getAll();
    public List <Users> getAllActive();
    public Optional<Users> findByName(String name);
    public Optional<Users> find (int id);
    public void update (Users users);
    public void delete (int id, boolean activo);
}
