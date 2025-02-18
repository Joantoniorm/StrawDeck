package com.strawdecks.strawdeck.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.strawdecks.strawdeck.modelo.Users;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UsersDaoImpl implements UsersDao {
    @Autowired
        private JdbcTemplate jdbcTemplate;
    @Override
    public void create(Users users) {
        String sqlInsert = """
                INSERT INTO users (id, username, password, gmail, activo) VALUES (?,?,?,?,?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
            int idx = 1;
            ps.setString(idx++, users.getName());
            ps.setString(idx++, users.getPassword());
            ps.setString(idx++, users.getGmail());
            ps.setBoolean(idx, true);
            return ps;
        }, keyHolder);
    
        users.setId(keyHolder.getKey().intValue());
    }
    @Override
    public List<Users> getAll() {
        List<Users> listUsers = jdbcTemplate.query("Select * from users", (rs, rowNum) -> new Users(
            rs.getInt("id"), 
            rs.getString("name"), 
            rs.getString("password"),
            rs.getString("gmail"),
            rs.getBoolean("activo"))
            );
            return listUsers;
    }

    @Override 
    public List<Users> getAllActive(){
        List<Users> listUsers = jdbcTemplate.query("Select * from users WHERE activo = true", (rs, rowNum) -> new Users(
            rs.getInt("id"), 
            rs.getString("name"), 
            rs.getString("password"),
            rs.getString("gmail"),
            rs.getBoolean("activo"))
            );
            return listUsers;
    }

    @Override
    public Optional<Users> find(String name) {
        Users user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE name = ?", (rs, rowNum)->
        new Users(
        rs.getInt("id"), 
        rs.getString("name"), 
        rs.getString("password"),
        rs.getString("gmail"),
        rs.getBoolean("activo"))
        );
        if (user!=null) {
            return Optional.of(user);
        } else{
            System.out.println("No existe usuario");
            return Optional.empty();
        }
    }

    @Override
    public void update(Users users) {
        int rows = jdbcTemplate.update("""
                UPDATE users SET 
                username = ?,
                password =?,
                gmail=?
                WHERE id = ?
                """, users.getName(),
                users.getPassword(),
                users.getGmail(),
                users.getId());
    }

    @Override
    public void delete(int id, boolean activo) {
        Integer count;
        
        try {
            count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE id = ?", Integer.class, id
            );
        } catch (EmptyResultDataAccessException e) {
            count = 0; 
        }
        if (count != null && count > 0) {
            int rows = jdbcTemplate.update(
                "UPDATE users SET activo = ? WHERE id = ?", activo, id
            );
            if (rows > 0) {
                System.out.println("Usuario con ID " + id + " actualizado");
            } else {
                System.out.println("No se pudo eliminar el usuario con ID: " + id);
            }
        } else {
            System.out.println("El usuario con ID " + id + " no existe");
        }
    }

    
}
