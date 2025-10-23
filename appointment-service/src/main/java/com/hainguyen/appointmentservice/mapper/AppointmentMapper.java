package com.hainguyen.appointmentservice.mapper;

import com.hainguyen.appointmentservice.dto.AppointmentResponseDto;
import com.hainguyen.appointmentservice.entity.Appointment;
import com.hainguyen.appointmentservice.entity.CachedPatient;

public class AppointmentMapper {

    public static AppointmentResponseDto toDto(Appointment appointment) {
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setId(appointment.getId());
        appointmentResponseDto.setPatientId(appointment.getPatientId());
        //appointmentResponseDto.setPatientName();
        appointmentResponseDto.setStartTime(appointment.getStartTime());
        appointmentResponseDto.setEndTime(appointment.getEndTime());
        appointmentResponseDto.setReason(appointment.getReason());
        appointmentResponseDto.setVersion(appointment.getVersion());

        return appointmentResponseDto;
    }
}
