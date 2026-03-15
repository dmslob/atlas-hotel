package com.dmslob.roomservice.config;

import com.dmslob.roomservice.repository.RoomRepository;
import com.dmslob.roomservice.service.RoomService;
import com.dmslob.roomservice.service.RoomServiceImpl;
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
    RoomService roomService(
            RoomRepository repository,
            ModelMapper modelMapper
    ) {
        return new RoomServiceImpl(repository, modelMapper);
    }
}
