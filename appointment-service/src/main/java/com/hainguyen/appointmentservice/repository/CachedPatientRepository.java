package com.hainguyen.appointmentservice.repository;

import com.hainguyen.appointmentservice.entity.CachedPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CachedPatientRepository extends JpaRepository<CachedPatient, UUID> {

}
