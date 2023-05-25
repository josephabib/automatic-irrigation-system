package com.banque.misr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutomaticIrrigationSystem {

    public static void main(String[] args) {
        SpringApplication.run(AutomaticIrrigationSystem.class, args);
    }
}