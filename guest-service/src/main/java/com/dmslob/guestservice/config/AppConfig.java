package com.dmslob.guestservice.config;

import com.dmslob.guestservice.repository.GuestRepository;
import com.dmslob.guestservice.service.GuestService;
import com.dmslob.guestservice.service.GuestServiceImpl;
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
    public GuestService guestService(
            GuestRepository guestRepository, ModelMapper modelMapper) {
        return new GuestServiceImpl(guestRepository, modelMapper);
    }
}
