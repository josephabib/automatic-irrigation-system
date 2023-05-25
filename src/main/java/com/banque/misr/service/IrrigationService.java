package com.banque.misr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IrrigationService {

    PlotService plotService;
    CircuitBreakerFactory circuitBreakerFactory;
    @Value("${irrigation.service.service-name}")
    private String SERVICE_NAME;
    @Value("${irrigation.service.irrigate-url}")
    private String IRRIGATE_URL;
    public IrrigationService(PlotService plotService, CircuitBreakerFactory circuitBreakerFactory) {
        this.plotService = plotService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public int irrigateLand(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create(SERVICE_NAME);
        circuitBreaker.run(() -> restTemplate.getForObject(IRRIGATE_URL, String.class), throwable -> alert(id));
        return 1;
    }

    private int alert(Long id) {
        System.out.println("ALERT - Failed to irrigate land ID: " + id + ", Sensor is not available");
        return 0;
    }
}
