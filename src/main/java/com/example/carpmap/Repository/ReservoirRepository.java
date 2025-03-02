package com.example.carpmap.Repository;

import com.example.carpmap.Models.DTO.ReservoirRepositoryDTO;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Models.Enums.ReservoirType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ReservoirRepository extends JpaRepository<Reservoir, Long> {

    Optional<Reservoir> findByName(String name);

    Optional<Reservoir> findByUrlName(String name);

    Page<Reservoir> findAllByName(String name, Pageable pageable);

    Page<Reservoir> findAllByNameContaining(String name, Pageable pageable);

    Page<Reservoir> findAllByUrlNameContaining(String name, Pageable pageable);

    Page<Reservoir> findAllByReservoirType(ReservoirType reservoirType, Pageable pageable);

    @Query("SELECT r.mainUrlImage FROM Reservoir r")
    List<String> findAllMainUrlImages();

    @Query("SELECT new com.example.carpmap.Models.DTO.ReservoirRepositoryDTO(r.name, r.mainUrlImage, r.urlName) FROM Reservoir r" )
    List<ReservoirRepositoryDTO> findAllNameAndImageUrl();

}

