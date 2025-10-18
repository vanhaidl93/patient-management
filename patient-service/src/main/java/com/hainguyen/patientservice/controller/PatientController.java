package com.hainguyen.patientservice.controller;

import com.hainguyen.patientservice.dto.PatientRequestDTO;
import com.hainguyen.patientservice.dto.PatientResponseDTO;
import com.hainguyen.patientservice.dto.validators.CreatePatientValidationGroup;
import com.hainguyen.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient", description = "API for managing Patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        var patientResponseDTOs = patientService.getPatients();
        return ResponseEntity.ok(patientResponseDTOs);
    }

    @PostMapping
    @Operation(summary = "Create an new Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class})
                                                            @RequestBody PatientRequestDTO patientRequestDTO) {
        var patientResponseDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.ok(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a  Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
                                                            @Validated({Default.class})
                                                            @RequestBody PatientRequestDTO patientRequestDTO) {
        // without @Valid, no validation rules will be checked, annotation will silently pass.
        var patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);

        return ResponseEntity.ok(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }


}

