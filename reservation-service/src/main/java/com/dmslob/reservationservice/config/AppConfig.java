package com.dmslob.reservationservice.config;

import com.dmslob.reservationservice.repository.ReservationRepository;
import com.dmslob.reservationservice.service.ReservationService;
import com.dmslob.reservationservice.service.ReservationServiceImpl;
import com.dmslob.reservationservice.service.client.GuestFallback;
import com.dmslob.reservationservice.service.client.GuestFeignClient;
import com.dmslob.reservationservice.service.client.RoomFallback;
import com.dmslob.reservationservice.service.client.RoomFeignClient;
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
            ModelMapper modelMapper,
            GuestFeignClient guestFeignClient,
            RoomFeignClient roomFeignClient) {
        return new ReservationServiceImpl(
                reservationRepository,
                modelMapper,
                guestFeignClient,
                roomFeignClient);
    }

    @Bean
    public GuestFallback guestFallback() {
        return new GuestFallback();
    }

    @Bean
    public RoomFallback roomFallback() {
        return new RoomFallback();
    }
}
