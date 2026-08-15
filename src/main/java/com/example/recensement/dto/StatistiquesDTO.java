package com.example.recensement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class StatistiquesDTO {
    private Long populationTotale;
    private Long nombreMenages;
    private Double tailleMoyenneMenage;
    private String zonePlusPeuplee;
    private String zoneAgeMoyenLePlusBas;
    private String zoneAgeMoyenLePlusEleve;
    private String zoneTauxSurpeuplementLePlusEleve;
    private String typeLogementDominantParZone;
}
