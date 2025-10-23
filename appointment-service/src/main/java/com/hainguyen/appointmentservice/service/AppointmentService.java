package com.hainguyen.appointmentservice.service;

import com.hainguyen.appointmentservice.dto.AppointmentResponseDto;
import com.hainguyen.appointmentservice.entity.CachedPatient;
import com.hainguyen.appointmentservice.mapper.AppointmentMapper;
import com.hainguyen.appointmentservice.repository.AppointmentRepository;
import com.hainguyen.appointmentservice.repository.CachedPatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CachedPatientRepository cachedPatientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, CachedPatientRepository cachedPatientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.cachedPatientRepository = cachedPatientRepository;
    }

    public List<AppointmentResponseDto> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {

        return appointmentRepository.findByStartTimeBetween(start,end).stream()
                .map(appointment -> {
                    String patientName = cachedPatientRepository.findById(appointment.getPatientId())
                            .map(CachedPatient::getFullName)
                            .orElse("Unknown");
                    AppointmentResponseDto appointmentResponseDto = AppointmentMapper.toDto(appointment);
                    appointmentResponseDto.setPatientName(patientName);
                    return appointmentResponseDto;
                }).toList();
    }

}
