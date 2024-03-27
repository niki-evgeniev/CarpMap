package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.CountryDTO;
import com.example.carpmap.Models.Entity.Country;
import com.example.carpmap.Repository.CountryRepository;
import com.example.carpmap.Service.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.carpmap.Cammon.SuccessfulMessages.*;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addFirstCountry() {
        if (countryRepository.count() == 0) {
            Country bulgariaCountry = new Country();
            bulgariaCountry.setCountry("Bulgaria");
            bulgariaCountry.setCountryCode("BG");
            countryRepository.save(bulgariaCountry);
            System.out.printf(SUCCESSFUL_REGISTER_COUNTRY,
                    bulgariaCountry.getCountry(), bulgariaCountry.getCountryCode());
        }
    }

    @Override
    public List<CountryDTO> getAllCountry() {
        List<Country> all = countryRepository.findAll();
        List<CountryDTO> allCountryDTO = new ArrayList<>();

        for (Country country : all) {
            CountryDTO map = modelMapper.map(country, CountryDTO.class);
            allCountryDTO.add(map);
        }

        return allCountryDTO;
    }
}
