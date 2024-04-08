package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {

    Optional<Fish> findByFishName(String fishName);

}
