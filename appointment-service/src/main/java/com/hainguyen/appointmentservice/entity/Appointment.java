package com.hainguyen.appointmentservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "patientId is required")
    @Column(nullable = false)
    private UUID patientId;

    @NotNull(message = "startTime is required")
    @Column(nullable = false)
    @Future(message = "startTime must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "endTime is required")
    @Column(nullable = false)
    @Future(message = "endTime must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "reason is required")
    @Size(max = 255, message = "reason must be 255 characters or less")
    @Column(nullable = false, length = 255)
    private String reason;

    // prevents concurrent update conflicts on the same database record
    // managed automatically by JPA
    @Version
    @Column(nullable = false)
    private Long version;

    public Appointment(UUID id, UUID patientId, LocalDateTime startTime, LocalDateTime endTime, String reason) {
        this.id = id;
        this.patientId = patientId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
    }

    public Appointment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull(message = "patientId is required") UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(@NotNull(message = "patientId is required") UUID patientId) {
        this.patientId = patientId;
    }

    public @NotNull(message = "startTime is required") @Future(message = "startTime must be in the future") LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(@NotNull(message = "startTime is required") @Future(message = "startTime must be in the future") LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public @NotNull(message = "endTime is required") @Future(message = "endTime must be in the future") LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(@NotNull(message = "endTime is required") @Future(message = "endTime must be in the future") LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public @NotNull(message = "reason is required") @Size(max = 255, message = "reason must be 255 characters or less") String getReason() {
        return reason;
    }

    public void setReason(@NotNull(message = "reason is required") @Size(max = 255, message = "reason must be 255 characters or less") String reason) {
        this.reason = reason;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
