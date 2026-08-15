package com.example.recensement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MenageResponseDTO {
    private Long id;
    private String chefMenage;
    private String zone;
    private Integer nombrePersonnes;
    private Double ageMoyen;
    private String typeLogement;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
}
