package com.example.recensement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "menages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du chef de ménage est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom du chef de ménage doit contenir entre 2 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String chefMenage;

    @NotBlank(message = "La zone est obligatoire")
    @Size(min = 2, max = 100, message = "La zone doit contenir entre 2 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String zone;

    @NotNull(message = "Le nombre de personnes est obligatoire")
    @Min(value = 1, message = "Un ménage doit compter au moins 1 personne")
    @Max(value = 30, message = "Le nombre de personnes semble suspect (max 30)")
    @Column(nullable = false)
    private Integer nombrePersonnes;

    @NotNull(message = "L'âge moyen est obligatoire")
    @DecimalMin(value = "0.0", message = "L'âge moyen ne peut pas être négatif")
    @DecimalMax(value = "120.0", message = "L'âge moyen dépasse la limite réaliste")
    @Column(nullable = false)
    private Double ageMoyen;

    @NotBlank(message = "Le type de logement est obligatoire")
    @Size(min = 2, max = 50, message = "Le type de logement doit contenir entre 2 et 50 caractères")
    @Column(nullable = false, length = 50)
    private String typeLogement;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private LocalDateTime dateDerniereModification;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        this.dateDerniereModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateDerniereModification = LocalDateTime.now();
    }
}
