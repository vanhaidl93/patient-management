package com.hainguyen.appointmentservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentRequestDto {

    @NotNull(message = "patientId is required")
    private UUID patientId;

    @NotNull(message = "startTime is required ")
    @Future(message = "startTime must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "endTime is required")
    @Future(message = "endTime must be in the future")
    private LocalDateTime endTime;

    @NotBlank(message = "reason is required")
    @Size(max = 255, message = "reason must be 255 characters or less")
    private String reason;

    // Optional, if not sent, defaults to 0
    private Long version =0L;

    public AppointmentRequestDto() {
    }

    public AppointmentRequestDto(UUID patientId, LocalDateTime startTime, LocalDateTime endTime, String reason) {
        this.patientId = patientId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
    }

    public @NotNull(message = "patientId is required") UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(@NotNull(message = "patientId is required") UUID patientId) {
        this.patientId = patientId;
    }

    public @NotNull(message = "startTime is required ") @Future(message = "startTime must be in the future") LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(@NotNull(message = "startTime is required ") @Future(message = "startTime must be in the future") LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public @NotNull(message = "endTime is required") @Future(message = "endTime must be in the future") LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(@NotNull(message = "endTime is required") @Future(message = "endTime must be in the future") LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public @NotBlank(message = "reason is required") @Size(max = 255, message = "reason must be 255 characters or less") String getReason() {
        return reason;
    }

    public void setReason(@NotBlank(message = "reason is required") @Size(max = 255, message = "reason must be 255 characters or less") String reason) {
        this.reason = reason;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
