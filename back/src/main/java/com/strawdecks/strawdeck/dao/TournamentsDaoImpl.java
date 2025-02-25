package com.strawdecks.strawdeck.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.strawdecks.strawdeck.modelo.Tournaments;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TournamentsDaoImpl implements TournamentsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void create(Tournaments tournaments) {
        
        String sqlInsert = """
                INSERT INTO tournaments (id, user_id, name,activo ) VALUES (?,?,?,?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection->{
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String [] { "id" });
            int idx = 1;
            ps.setInt(idx++, tournaments.getUser_id());
            ps.setString(idx++, tournaments.getName());
            ps.setBoolean(idx, true);
            return ps;
        }, keyHolder);
        tournaments.setId(keyHolder.getKey().intValue());
    }

    @Override
    public List<Tournaments> getAll() {
        List<Tournaments> listTournaments =jdbcTemplate.query("Select * from tournaments",
         (rs, rowNum)-> new Tournaments(
            rs.getInt("id"),
            rs.getInt("user_id"), 
            rs.getString("name"),
            rs.getBoolean("activo"))
            );
            return listTournaments;
        
    }
    @Override
    public List<Tournaments> getAllActive() {
        List<Tournaments> listTournaments =jdbcTemplate.query("Select * from tournaments WHERE activo = true",
         (rs, rowNum)-> new Tournaments(
            rs.getInt("id"),
            rs.getInt("user_id"), 
            rs.getString("name"),
            rs.getBoolean("activo"))
            );
            return listTournaments;
        
    }

    @Override
    public Optional<Tournaments> find(int id) {
        Tournaments tournaments = jdbcTemplate.queryForObject("Select * from tournaments where id= ?", (rs, rowNum)->
        new Tournaments(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getBoolean("activo")));
            if (tournaments!=null) {
                return Optional.of(tournaments);
            }else{
                System.out.println("No existe torneo");
                return Optional.empty();
            }
    }

    @Override
    public void update(Tournaments tournaments) {
        int rows = jdbcTemplate.update("""
                UPDATE tournaments SET
                user_id = ?,
                name=? WHERE id= ?
                """, tournaments.getUser_id(),
                tournaments.getName(),
                tournaments.getId());
    }

    @Override
    public void delete(int id, boolean activo) {
       
        Integer count = jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM tournaments WHERE id = ?",
        Integer.class, id
        );

        if (count != null && count > 0) {

            int rows = jdbcTemplate.update(
                "UPDATE tournaments SET activo = ? WHERE id = ?",
                activo, id
            );

            if (rows > 0) {
                System.out.println("Torneo con ID " + id + " actualizado");
            } else {
                System.out.println("No se pudo actualizar el torneo con ID: " + id);
            }
        } else {
            System.out.println("El torneo con ID " + id + " no existe");
        }
    }
}
    

