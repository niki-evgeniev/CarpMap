package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.IpAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

    Optional<IpAddress> findByAddress(String address);

    @Query("SELECT SUM(i.countVisits) FROM IpAddress i")
    Long sumAllCounts();

    Page<IpAddress> findAllByUserIsNotNull(Pageable pageable);


}
