package com.strawdecks.strawdeck.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strawdecks.strawdeck.modelo.Tournaments;
import com.strawdecks.strawdeck.service.TournamentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @PostMapping("/create")
    public ResponseEntity<String> createTournament(@RequestBody Tournaments tournaments) {
        
        tournamentService.createTournament(tournaments);
        return ResponseEntity.ok("Torneo Creado")
        ;
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteTournament(@RequestParam int id){
        tournamentService.deleteTournament(id);
        return ResponseEntity.ok("Torneo eliminado");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTournament(@RequestBody Tournaments tournaments) {
        tournamentService.updateTournament(tournaments);
        
        return ResponseEntity.ok("Torneo Actualizado con Exito");
    }
    @GetMapping("/all")
    public ResponseEntity<List<Tournaments>> getAllTournaments () {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournaments> getTournamentById(@RequestParam int id) {
        Optional<Tournaments> tournament = tournamentService.find(id);
        return tournament.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    
}
