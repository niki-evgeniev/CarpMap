package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.Entity.Country;
import com.example.carpmap.Repository.CountryRepository;
import com.example.carpmap.Service.CountryService;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void addFirstCountry() {
        Country bulgariaCountry = new Country();
        bulgariaCountry.setCountry("Bulgaria");
        bulgariaCountry.setCountryCode("BG");
        countryRepository.save(bulgariaCountry);

    }
}
