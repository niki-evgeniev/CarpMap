package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.FishList;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface FishListRepository extends JpaRepository<FishList, Long> {

    Optional<FishList> findByFishName(String name);

    Optional<FishList> findByUrlName(String urlName);

}
