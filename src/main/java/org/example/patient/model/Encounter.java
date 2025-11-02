package org.example.patient.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="encounters")
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="patient_id")
    private Patient patient;

    @Column(name="start_time", nullable=false)
    private LocalDateTime start;

    @Column(name="end_time")
    private LocalDateTime end;

    @Column(name="encounter_class")
    private String encounterClass;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public LocalDateTime getStart() { return start; }
    public void setStart(LocalDateTime start) { this.start = start; }
    public LocalDateTime getEnd() { return end; }
    public void setEnd(LocalDateTime end) { this.end = end; }
    public String getEncounterClass() { return encounterClass; }
    public void setEncounterClass(String encounterClass) { this.encounterClass = encounterClass; }
}
