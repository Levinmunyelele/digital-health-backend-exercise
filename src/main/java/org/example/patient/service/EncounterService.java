package org.example.patient.service;

import org.example.exception.ResourceNotFoundException;
import org.example.patient.model.Encounter;
import org.example.patient.model.Patient;
import org.example.patient.repository.EncounterRepository;
import org.example.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncounterService {
    private final EncounterRepository encounterRepo;
    private final PatientRepository patientRepo;

    public EncounterService(EncounterRepository encounterRepo, PatientRepository patientRepo) {
        this.encounterRepo = encounterRepo;
        this.patientRepo = patientRepo;
    }

    public Encounter create(Long patientId, Encounter encounter) {
        Patient p = patientRepo.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId));
        encounter.setPatient(p);
        return encounterRepo.save(encounter);
    }

    public List<Encounter> findAllByPatient(Long patientId) {
        Patient p = patientRepo.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId));
        return p.getEncounters();
    }
}
