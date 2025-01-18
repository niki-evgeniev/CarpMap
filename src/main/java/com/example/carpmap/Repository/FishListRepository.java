package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.FishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishListRepository extends JpaRepository<FishList, Long> {

    Optional<FishList> findByFishName(String name);

    Optional<FishList> findByUrlName(String urlName);

    Page<FishList> findAllByUrlNameContaining(String urlName, Pageable pageable);

    Page<FishList> findAllByFishNameContaining (String name, Pageable pageable);

    Page<FishList> findAllByUrlName(String fishType, Pageable pageable);

    Page<FishList> findAllByFishName(String fishType, Pageable pageable);
}
