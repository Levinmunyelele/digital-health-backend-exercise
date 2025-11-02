package org.example.patient.repository;

import org.example.patient.model.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncounterRepository extends JpaRepository<Encounter, Long> { }

