package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class Beans {

    @Bean
    public Supplier<String> idGenerator() {
        return () -> UUID.randomUUID().toString();
    }

    @Bean
    public Supplier<Instant> timestampGenerator() {
        return Instant::now;
    }
}
