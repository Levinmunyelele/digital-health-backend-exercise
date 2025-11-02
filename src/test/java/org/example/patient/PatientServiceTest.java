package org.example.patient;

import org.example.patient.model.Patient;
import org.example.patient.repository.PatientRepository;
import org.example.patient.service.PatientService;
import org.example.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    private PatientRepository repo;
    private PatientService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(PatientRepository.class);
        service = new PatientService(repo);
    }

    @Test
    void shouldFindPatientById() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setIdentifier("P001");
        patient.setGivenName("Levin");
        patient.setFamilyName("Munyelele");
        patient.setBirthDate(LocalDate.of(1998, 3, 20));

        when(repo.findById(1L)).thenReturn(Optional.of(patient));

        Patient found = service.findById(1L);

        assertNotNull(found);
        assertEquals("Levin", found.getGivenName());
        assertEquals("P001", found.getIdentifier());
        assertEquals(LocalDate.of(1998, 3, 20), found.getBirthDate());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionIfPatientNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));

        verify(repo, times(1)).findById(99L);
    }

    @Test
    void shouldCreatePatientSuccessfully() {
        Patient patient = new Patient();
        patient.setIdentifier("P002");
        patient.setGivenName("Alice");
        patient.setFamilyName("Smith");
        patient.setBirthDate(LocalDate.of(1995, 5, 10));

        when(repo.save(any(Patient.class))).thenAnswer(invocation -> {
            Patient saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Patient created = service.create(patient);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals("Alice", created.getGivenName());
        assertEquals("P002", created.getIdentifier());
        verify(repo, times(1)).save(patient);
    }
}
