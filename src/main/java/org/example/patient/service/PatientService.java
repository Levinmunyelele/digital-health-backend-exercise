package org.example.patient.service;

import org.example.exception.ResourceNotFoundException;
import org.example.patient.model.Patient;
import org.example.patient.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private final PatientRepository repo;

    public PatientService(PatientRepository repo) { this.repo = repo; }

    public Patient create(Patient p) { return repo.save(p); }

    public Patient findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
    }

    public Page<Patient> search(Specification<Patient> spec, Pageable pageable){
        return repo.findAll(spec, pageable);
    }

    public Patient update(Long id, Patient updated) {
        Patient existing = findById(id);
        existing.setGivenName(updated.getGivenName());
        existing.setFamilyName(updated.getFamilyName());
        existing.setBirthDate(updated.getBirthDate());
        existing.setGender(updated.getGender());
        existing.setIdentifier(updated.getIdentifier());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Patient existing = findById(id);
        repo.delete(existing);
    }
}
