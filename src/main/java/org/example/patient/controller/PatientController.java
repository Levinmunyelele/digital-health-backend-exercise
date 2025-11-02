package org.example.patient.controller;

import org.example.patient.dto.PatientDto;
import org.example.patient.model.Patient;
import org.example.patient.service.PatientService;
import org.example.patient.spec.PatientSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService service;

    public PatientController(PatientService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody PatientDto dto) {
        Patient p = toEntity(dto);
        Patient saved = service.create(p);
        return ResponseEntity.created(URI.create("/api/patients/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDto dto) {
        Patient p = toEntity(dto);
        Patient updated = service.update(id, p);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String family,
            @RequestParam(required = false) String given,
            @RequestParam(required = false) String identifier,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        // Build dynamic filters
        Specification<Patient> spec = Specification.where(null);

        if (family != null && !family.isBlank()) {
            spec = spec.and(PatientSpecification.hasFamilyNameLike(family));
        }
        if (given != null && !given.isBlank()) {
            spec = spec.and(PatientSpecification.hasGivenNameLike(given));
        }
        if (identifier != null && !identifier.isBlank()) {
            spec = spec.and(PatientSpecification.hasIdentifier(identifier));
        }
        if (birthDateFrom != null && birthDateTo != null) {
            spec = spec.and(PatientSpecification.birthDateBetween(birthDateFrom, birthDateTo));
        }

        // ✅ Create the results Page BEFORE building the response
        Page<Patient> results = service.search(spec, PageRequest.of(page, size));

        // Build response with pagination metadata
        var response = new java.util.HashMap<String, Object>();
        response.put("content", results.getContent());
        response.put("currentPage", results.getNumber());
        response.put("pageSize", results.getSize());
        response.put("totalElements", results.getTotalElements());
        response.put("totalPages", results.getTotalPages());
        response.put("isLastPage", results.isLast());

        return ResponseEntity.ok(response);
    }
    // Convert DTO to entity
    private Patient toEntity(PatientDto dto) {
        Patient p = new Patient();
        p.setIdentifier(dto.getIdentifier());
        p.setGivenName(dto.getGivenName());
        p.setFamilyName(dto.getFamilyName());
        p.setBirthDate(dto.getBirthDate());
        p.setGender(dto.getGender());
        return p;
    }
}
