package com.example.recensement.service;

import com.example.recensement.dto.MenageRequestDTO;
import com.example.recensement.dto.MenageResponseDTO;
import com.example.recensement.dto.StatistiquesDTO;

import java.util.List;

public interface MenageService {
    MenageResponseDTO creerMenage(MenageRequestDTO dto);
    List<MenageResponseDTO> listerTousLesMenages();
    void supprimerMenage(Long id);
    StatistiquesDTO calculerStatistiques();
}
