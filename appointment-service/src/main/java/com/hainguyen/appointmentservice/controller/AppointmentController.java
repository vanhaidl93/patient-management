package com.hainguyen.appointmentservice.controller;

import com.hainguyen.appointmentservice.dto.AppointmentResponseDto;
import com.hainguyen.appointmentservice.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDateRange(@RequestParam LocalDateTime from,
                                                                                  @RequestParam LocalDateTime to) {

        return ResponseEntity.ok(appointmentService.getAppointmentsByDateRange(from,to));
    }
}
