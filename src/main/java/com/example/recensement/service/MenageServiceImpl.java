package com.example.recensement.service;

import com.example.recensement.dto.MenageRequestDTO;
import com.example.recensement.dto.MenageResponseDTO;
import com.example.recensement.dto.StatistiquesDTO;
import com.example.recensement.entity.Menage;
import com.example.recensement.exception.ResourceNotFoundException;
import com.example.recensement.repository.MenageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenageServiceImpl implements MenageService {
    private final MenageRepository menageRepository;

    @Override
    public MenageResponseDTO creerMenage(MenageRequestDTO dto) {
        Menage menage = new Menage();
        menage.setChefMenage(dto.getChefMenage());
        menage.setZone(dto.getZone());
        menage.setNombrePersonnes(dto.getNombrePersonnes());
        menage.setAgeMoyen(dto.getAgeMoyen());
        menage.setTypeLogement(dto.getTypeLogement());

        Menage sauvegarde = menageRepository.save(menage);
        return versDTO(sauvegarde);
    }

    @Override
    public List<MenageResponseDTO> listerTousLesMenages() {
        return menageRepository.findAll()
                .stream()
                .map(this::versDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerMenage(Long id) {
        if (!menageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aucun ménage trouvé avec l'identifiant : " + id);
        }
        menageRepository.deleteById(id);
    }

    @Override
    public StatistiquesDTO calculerStatistiques() {
        List<Menage> menages = menageRepository.findAll();

        if (menages.isEmpty()) {
            return StatistiquesDTO.builder()
                    .populationTotale(0L)
                    .nombreMenages(0L)
                    .tailleMoyenneMenage(0.0)
                    .zonePlusPeuplee("Aucune donnée")
                    .zoneAgeMoyenLePlusBas("Aucune donnée")
                    .zoneAgeMoyenLePlusEleve("Aucune donnée")
                    .zoneTauxSurpeuplementLePlusEleve("Aucune donnée")
                    .typeLogementDominantParZone("Aucune donnée")
                    .build();
        }

        long populationTotale = menages.stream()
                .mapToLong(Menage::getNombrePersonnes)
                .sum();

        long nombreMenages = menages.size();
        double tailleMoyenneMenage = Math.round((double) populationTotale / nombreMenages * 100.0) / 100.0;

        Map<String, Long> populationParZone = menages.stream()
                .collect(Collectors.groupingBy(Menage::getZone, Collectors.summingLong(Menage::getNombrePersonnes)));

        String zonePlusPeuplee = populationParZone.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Aucune donnée");

        Map<String, Double> ageMoyenParZone = menages.stream()
                .collect(Collectors.groupingBy(Menage::getZone, Collectors.averagingDouble(Menage::getAgeMoyen)));

        String zoneAgeMoyenLePlusBas = ageMoyenParZone.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Aucune donnée");

        String zoneAgeMoyenLePlusEleve = ageMoyenParZone.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Aucune donnée");

        // Taux de surpeuplement = nombre moyen de personnes par ménage, par zone
        Map<String, Double> moyennePersonnesParZone = menages.stream()
                .collect(Collectors.groupingBy(Menage::getZone, Collectors.averagingInt(Menage::getNombrePersonnes)));

        String zoneTauxSurpeuplementLePlusEleve = moyennePersonnesParZone.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Aucune donnée");

        // Type de logement dominant, zone par zone
        Map<String, String> dominantParZone = menages.stream()
                .collect(Collectors.groupingBy(Menage::getZone))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entree -> entree.getValue().stream()
                                .collect(Collectors.groupingBy(Menage::getTypeLogement, Collectors.counting()))
                                .entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("Inconnu")
                ));

        String typeLogementDominantParZone = dominantParZone.entrySet().stream()
                .map(entree -> entree.getKey() + " : " + entree.getValue())
                .collect(Collectors.joining(", "));

        return StatistiquesDTO.builder()
                .populationTotale(populationTotale)
                .nombreMenages(nombreMenages)
                .tailleMoyenneMenage(tailleMoyenneMenage)
                .zonePlusPeuplee(zonePlusPeuplee)
                .zoneAgeMoyenLePlusBas(zoneAgeMoyenLePlusBas)
                .zoneAgeMoyenLePlusEleve(zoneAgeMoyenLePlusEleve)
                .zoneTauxSurpeuplementLePlusEleve(zoneTauxSurpeuplementLePlusEleve)
                .typeLogementDominantParZone(typeLogementDominantParZone)
                .build();
    }

    private MenageResponseDTO versDTO(Menage menage) {
        return MenageResponseDTO.builder()
                .id(menage.getId())
                .chefMenage(menage.getChefMenage())
                .zone(menage.getZone())
                .nombrePersonnes(menage.getNombrePersonnes())
                .ageMoyen(menage.getAgeMoyen())
                .typeLogement(menage.getTypeLogement())
                .dateCreation(menage.getDateCreation())
                .dateDerniereModification(menage.getDateDerniereModification())
                .build();
    }

}
