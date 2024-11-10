package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.FishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishListRepository extends JpaRepository<FishList, Long> {

    Optional<FishList> findByFishName(String name);
}
