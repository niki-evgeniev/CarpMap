package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.CountryDTO;
import com.example.carpmap.Models.Entity.Country;
import com.example.carpmap.Repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl(countryRepository, modelMapper);
    }

    @Test
    void testAddFirstCountry_WhenNoCountriesExist() {
        when(countryRepository.count()).thenReturn(0L);

        countryService.addFirstCountry();

        verify(countryRepository, times(2)).save(any(Country.class));
    }

    @Test
    void testAddFirstCountry_WhenCountriesExist() {
        when(countryRepository.count()).thenReturn(1L);

        countryService.addFirstCountry();

        verify(countryRepository, never()).save(any(Country.class));
    }

    @Test
    void testGetAllCountry() {
        Country country1 = new Country();
        country1.setCountry("Bulgaria");
        country1.setCountryCode("BG");

        Country country2 = new Country();
        country2.setCountry("Germany");
        country2.setCountryCode("GN");

        List<Country> countryList = Arrays.asList(country1, country2);
        when(countryRepository.findAll()).thenReturn(countryList);

        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setCountry("Bulgaria");

        CountryDTO countryDTO2 = new CountryDTO();
        countryDTO2.setCountry("Germany");

        when(modelMapper.map(country1, CountryDTO.class)).thenReturn(countryDTO1);
        when(modelMapper.map(country2, CountryDTO.class)).thenReturn(countryDTO2);

        List<CountryDTO> result = countryService.getAllCountry();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Bulgaria", result.get(0).getCountry());

        verify(countryRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(Country.class), eq(CountryDTO.class));
    }
}
