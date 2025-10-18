package com.hainguyen.patientservice.mapper;

import com.hainguyen.patientservice.dto.PatientRequestDTO;
import com.hainguyen.patientservice.dto.PatientResponseDTO;
import com.hainguyen.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient) {
        var dto = new PatientResponseDTO();
        dto.setId(patient.getId().toString());
        dto.setAddress(patient.getAddress());
        dto.setEmail(patient.getEmail());
        dto.setName(patient.getName());
        dto.setDateOfBirth(patient.getDateOfBirth().toString());
        return dto;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        var model = new Patient();
        model.setName(patientRequestDTO.getName());
        model.setAddress(patientRequestDTO.getAddress());
        model.setEmail(patientRequestDTO.getEmail());
        model.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        model.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return model;
    }
}
