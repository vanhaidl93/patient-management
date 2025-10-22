package com.hainguyen.patientservice.aspects;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PatientServiceMetrics {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceMetrics.class);
    private final MeterRegistry meterRegistry;

    public PatientServiceMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("execution(* com.hainguyen.patientservice.service.PatientService.getPatients(..))")
    public Object monitorGetPatients(ProceedingJoinPoint joinPoint) throws Throwable {
        meterRegistry.counter("custom.redis.cache.miss","cache","patients").increment();

        log.info("[REDIS CACHE MISSING] custom.redis.cache.miss");
        return joinPoint.proceed();
    }
}
