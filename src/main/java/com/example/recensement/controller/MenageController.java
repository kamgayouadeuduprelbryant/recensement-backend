package com.example.recensement.controller;

import com.example.recensement.dto.MenageRequestDTO;
import com.example.recensement.dto.MenageResponseDTO;
import com.example.recensement.dto.StatistiquesDTO;
import com.example.recensement.service.MenageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menages")
@RequiredArgsConstructor

public class MenageController {
    private final MenageService menageService;

    @PostMapping
    public ResponseEntity<MenageResponseDTO> creerMenage(@Valid @RequestBody MenageRequestDTO dto) {
        MenageResponseDTO cree = menageService.creerMenage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cree);
    }

    @GetMapping
    public ResponseEntity<List<MenageResponseDTO>> listerMenages() {
        return ResponseEntity.ok(menageService.listerTousLesMenages());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerMenage(@PathVariable Long id) {
        menageService.supprimerMenage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistiques")
    public ResponseEntity<StatistiquesDTO> obtenirStatistiques() {
        return ResponseEntity.ok(menageService.calculerStatistiques());
    }
}
