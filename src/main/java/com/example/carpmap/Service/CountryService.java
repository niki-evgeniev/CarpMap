package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.AllCountryDTO;

import java.util.List;

public interface CountryService {

    void addFirstCountry();

    List<AllCountryDTO> getAllCountry();
}
