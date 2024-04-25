package com.example.carpmap.AppConfiguration;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsEditDTO;
import com.example.carpmap.Models.Entity.Reservoir;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        modelMapper
                .createTypeMap(ReservoirsEditDTO.class, Reservoir.class)
                .addMappings(mapper -> mapper.map(ReservoirsEditDTO::getCity,Reservoir::setCity));

        return modelMapper;
    }
}
