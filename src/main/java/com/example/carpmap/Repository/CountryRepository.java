package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
