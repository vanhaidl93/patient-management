package com.hainguyen.patientservice.service;

import com.hainguyen.patientservice.dto.PagedPatientResponseDTO;
import com.hainguyen.patientservice.dto.PatientRequestDTO;
import com.hainguyen.patientservice.dto.PatientResponseDTO;
import com.hainguyen.patientservice.exception.EmailAlreadyExistsException;
import com.hainguyen.patientservice.exception.PatientNotFoundException;
import com.hainguyen.patientservice.grpc.BillingServiceGrpcClient;
import com.hainguyen.patientservice.kafka.KafkaProducer;
import com.hainguyen.patientservice.mapper.PatientMapper;
import com.hainguyen.patientservice.model.Patient;
import com.hainguyen.patientservice.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public PagedPatientResponseDTO getPatients(int page, int size, String sort, String sortField, String searchValue) {

        Pageable pageable = PageRequest.of(page-1, size,
                sort.equalsIgnoreCase("desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending());

        Page<Patient> patientPage;
        if (searchValue == null || searchValue.isBlank()) {
            patientPage = patientRepository.findAll(pageable);
        } else {
            patientPage = patientRepository.findByNameContainingIgnoreCase(pageable, searchValue);
        }

        return new PagedPatientResponseDTO(
                patientPage.stream().map(PatientMapper::toDTO).toList(),
                patientPage.getNumber() +1,
                patientPage.getSize(),
                patientPage.getTotalPages(),
                (int) patientPage.getTotalElements());
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists " + patientRequestDTO.getEmail());
        }
        Patient newPatient = this.patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        // create a billing Account.
        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                newPatient.getName(), newPatient.getEmail());

        // send event to topic: 'patient'.
        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + id)
        );

        // if there is another patient in the database wit the same email (IdNot)
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }


}
