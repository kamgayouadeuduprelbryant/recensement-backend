package com.example.recensement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MenageRequestDTO {
    @NotBlank(message = "Le nom du chef de ménage est obligatoire")
    @Size(min = 2, max = 100)
    private String chefMenage;

    @NotBlank(message = "La zone est obligatoire")
    @Size(min = 2, max = 100)
    private String zone;

    @NotNull
    @Min(1)
    @Max(30)
    private Integer nombrePersonnes;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("120.0")
    private Double ageMoyen;

    @NotBlank
    @Size(min = 2, max = 50)
    private String typeLogement;
}
