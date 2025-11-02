package org.example.patient.spec;

import org.example.patient.model.Patient;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public final class PatientSpecification {

    public static Specification<Patient> hasFamilyNameLike(String family) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("familyName")), "%" + family.toLowerCase() + "%");
    }

    public static Specification<Patient> hasGivenNameLike(String given) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("givenName")), "%" + given.toLowerCase() + "%");
    }

    public static Specification<Patient> hasIdentifier(String identifier) {
        return (root, query, cb) -> cb.equal(root.get("identifier"), identifier);
    }

    public static Specification<Patient> birthDateBetween(LocalDate from, LocalDate to) {
        return (root, query, cb) -> cb.between(root.get("birthDate"), from, to);
    }
}
