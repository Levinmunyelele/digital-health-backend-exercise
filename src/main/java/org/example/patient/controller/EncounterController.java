package org.example.patient.controller;

import org.example.patient.model.Encounter;
import org.example.patient.service.EncounterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patients/{patientId}/encounters")
public class EncounterController {
    private final EncounterService service;

    public EncounterController(EncounterService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Encounter> create(@PathVariable Long patientId, @Valid @RequestBody Encounter encounter) {
        Encounter saved = service.create(patientId, encounter);
        return ResponseEntity.created(URI.create("/api/patients/" + patientId + "/encounters/" + saved.getId()))
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Encounter>> list(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.findAllByPatient(patientId));
    }
}
