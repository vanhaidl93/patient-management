package com.hainguyen.patientservice.repository;

import com.hainguyen.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail(String email);

    // if check other patient (IdNot) have same email ?
    boolean existsByEmailAndIdNot(String email, UUID id);


}
