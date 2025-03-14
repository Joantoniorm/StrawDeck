package com.strawdecks.strawdeck.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
                INSERT INTO users (username, password, gmail, activo) VALUES (?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            int idx = 1;
            ps.setString(idx++, users.getUsername());
            ps.setString(idx++, users.getPassword());
            ps.setString(idx++, users.getGmail());
            ps.setBoolean(idx++, true);
            return ps;
        }, keyHolder);
    
        users.setId(keyHolder.getKey().intValue());
    }

    @Override
    public List<Users> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) -> 
            new Users(
                rs.getInt("id"), 
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("gmail"),
                rs.getBoolean("activo")
            )
        );
    }

    @Override 
    public List<Users> getAllActive() {
        return jdbcTemplate.query("SELECT * FROM users WHERE activo = true", (rs, rowNum) -> 
            new Users(
                rs.getInt("id"), 
                rs.getString("username"),  
                rs.getString("password"),
                rs.getString("gmail"),
                rs.getBoolean("activo")
            )
        );
    }

    @Override
    public Optional<Users> findByName(String username) {
        try {
            Users user = jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE username = ? LIMIT 1",  // LIMIT 1 asegura que solo se devuelva un usuario
                (rs, rowNum) -> new Users(
                    rs.getInt("id"), 
                    rs.getString("username"),  
                    rs.getString("password"),
                    rs.getString("gmail"),
                    rs.getBoolean("activo")
                ),
                username
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No existe usuario con username: {}", username);
            return Optional.empty();
        }
    }
    @Override
    public Optional<Users> find(int id) {
        try {
            Users user = jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?", 
                (rs, rowNum) -> new Users(
                    rs.getInt("id"), 
                    rs.getString("username"),  
                    rs.getString("password"),
                    rs.getString("gmail"),
                    rs.getBoolean("activo")
                ),
                id
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No existe usuario con username: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public void update(Users users) {
        int rows = jdbcTemplate.update("""
                UPDATE users SET 
                username = ?,  
                password = ?,
                gmail = ?
                WHERE id = ?
                """, 
                users.getUsername(),  
                users.getPassword(),
                users.getGmail(),
                users.getId()
        );
        
        if (rows > 0) {
            log.info("Usuario con ID {} actualizado correctamente", users.getId());
        } else {
            log.warn("No se encontró el usuario con ID {}", users.getId());
        }
    }

    @Override
    public void delete(int id, boolean activo) {
        int rows = jdbcTemplate.update(
            "UPDATE users SET activo = ? WHERE id = ?", activo, id
        );

        if (rows > 0) {
            log.info("Usuario con ID {} actualizado correctamente (activo = {})", id, activo);
        } else {
            log.warn("No se pudo actualizar el usuario con ID {}", id);
        }
    }
}
