package com.example.carpmap.AppConfiguration;


import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsEditDTO;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Models.Entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, String> nullToNoneString = new Converter<String, String>() {
            @Override
            public String convert(MappingContext<String, String> mappingContext) {
                String source = mappingContext.getSource();
                return (source == null || source.isEmpty()) ? "none" : source;
            }
        };

        modelMapper
                .createTypeMap(ReservoirsEditDTO.class, Reservoir.class)
                .addMappings(mapper -> mapper.map(ReservoirsEditDTO::getCity, Reservoir::setCity));

        modelMapper
                .createTypeMap(User.class, ProfileInfoDTO.class)
                .addMappings(mapper -> {
                    mapper.using(nullToNoneString).map(User::getCity, ProfileInfoDTO::setCity);
                    mapper.using(nullToNoneString).map(User::getCountry, ProfileInfoDTO::setCountry);
                    mapper.using(nullToNoneString).map(User::getTeam, ProfileInfoDTO::setTeam);
                    mapper.using(nullToNoneString).map(User::getPhoneNumber, ProfileInfoDTO::setPhone);


                });
        return modelMapper;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
