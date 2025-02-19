package com.strawdecks.strawdeck.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.TournamentsDao;
import com.strawdecks.strawdeck.modelo.Tournaments;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TournamentService {
    @Autowired
    private TournamentsDao tournamentsDao;

    public void createTournament (Tournaments tournaments){
        tournamentsDao.create(tournaments);
    }
    public void updateTournament(Tournaments tournaments){
        tournamentsDao.update(tournaments);
    }
    public void deleteTournament (int id){
        tournamentsDao.delete(id, false);
    }
    public List<Tournaments> getAllTournaments(){
        return tournamentsDao.getAllActive();
    }
    public Optional<Tournaments> find(int id){
        return tournamentsDao.find(id);
    }
}
