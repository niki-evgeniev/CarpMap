package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.IpAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

    Optional<IpAddress> findByAddress(String address);

    @Query("SELECT SUM(i.countVisits) FROM IpAddress i")
    Long sumAllCounts();

    Page<IpAddress> findAllByUserIsNotNull(Pageable pageable);

    @Query("SELECT ip FROM IpAddress ip WHERE (ip.lastSeen >= :thirtyDaysAgo OR ip.timeToAdd >= :thirtyDaysAgo)")
    Page<IpAddress> findAllIpAddressesFromLast30Days(@Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo, Pageable pageable);
<<<<<<< HEAD

    @Query("SELECT ip FROM IpAddress ip WHERE (ip.lastSeen >= :lastDay OR ip.timeToAdd >= :lastDay)")
    Page<IpAddress> findAllIpAddressesFromLastDay(@Param("lastDay") LocalDateTime lastDay, Pageable pageable);

=======
>>>>>>> 9ee174badf832ab0d476de8a750d63984f01a071

    @Query("SELECT ip FROM IpAddress ip WHERE (ip.lastSeen >= :lastDay OR ip.timeToAdd >= :lastDay)")
    Page<IpAddress> findAllIpAddressesFromLastDay(@Param("lastDay") LocalDateTime thirtyDaysAgo, Pageable pageable);
}
