package com.example.carpmap.AppConfiguration;

import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsEditDTO;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Models.Entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
//        modelMapper
//                .createTypeMap(User.class, ProfileEditDTO.class)
//                .addMappings(mapper -> {
//                    mapper.using(nullToNoneString).map(User::getUsername, ProfileEditDTO::set);
//                    mapper.using(nullToNoneString).map(User::getPassword, ProfileEditDTO::set);
//                    mapper.using(nullToNoneString).map(User::getCreateOn, ProfileEditDTO::set);
//                    mapper.using(nullToNoneString).map(User::getPhoneNumber, ProfileEditDTO::setPhone);
//                });


        return modelMapper;
    }
}
