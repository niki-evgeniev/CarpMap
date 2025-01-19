package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.FishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishListRepository extends JpaRepository<FishList, Long> {

    Optional<FishList> findByName(String name);

    Optional<FishList> findByUrlName(String urlName);

    Page<FishList> findAllByNameContaining(String name, Pageable pageable);

    Page<FishList> findAllByUrlNameContaining(String name, Pageable pageable);

}
