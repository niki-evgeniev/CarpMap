package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.CountryDTO;

import java.util.List;

public interface CountryService {

    void addFirstCountry();

    List<CountryDTO> getAllCountry();
}
