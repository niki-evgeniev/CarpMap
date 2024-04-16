package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCountry(String country);

}
