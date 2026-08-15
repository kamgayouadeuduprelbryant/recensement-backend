package com.example.recensement.repository;

import com.example.recensement.entity.Menage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenageRepository extends JpaRepository<Menage, Long> {
    List<Menage> findByZoneIgnoreCase(String zone);

}
