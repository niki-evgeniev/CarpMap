package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.Reservoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservoirRepository extends JpaRepository<Reservoir, Long> {
}
