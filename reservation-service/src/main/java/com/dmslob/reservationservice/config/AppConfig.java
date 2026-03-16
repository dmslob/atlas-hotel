package com.dmslob.reservationservice.config;

import com.dmslob.reservationservice.repository.ReservationRepository;
import com.dmslob.reservationservice.service.ReservationService;
import com.dmslob.reservationservice.service.ReservationServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    ReservationService reservationService(
            ReservationRepository reservationRepository,
            ModelMapper modelMapper) {
        return new ReservationServiceImpl(reservationRepository, modelMapper);
    }
}
