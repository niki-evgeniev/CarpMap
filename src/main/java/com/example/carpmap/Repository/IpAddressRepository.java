package com.example.carpmap.Repository;

import com.example.carpmap.Models.Entity.IpAddress;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

    Optional<IpAddress> findByAddress(String address);

    @Query("SELECT SUM(i.countVisits) FROM IpAddress i")
    Long sumAllCounts();

    Page<IpAddress> findAllByUserIsNotNull(Pageable pageable);

    @Query("SELECT ip FROM IpAddress ip WHERE (ip.lastSeen >= :thirtyDaysAgo OR ip.timeToAdd >= :thirtyDaysAgo)")
    Page<IpAddress> findAllIpAddressesFromLast30Days
            (@Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo, Pageable pageable);

    @Query("SELECT ip FROM IpAddress ip WHERE (ip.lastSeen >= :lastDay OR ip.timeToAdd >= :lastDay)")
    Page<IpAddress> findAllIpAddressesFromLastDay
            (@Param("lastDay") LocalDateTime lastDay, Pageable pageable);

    @Query("SELECT COUNT(i) FROM IpAddress i WHERE i.lastSeen >= :startOfDay " +
            "AND i.lastSeen < :endOfDay")
    long countByLastSeenDateTime(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(i) FROM IpAddress i WHERE (i.timeToAdd  >= :startOfDay " +
            "AND i.timeToAdd < :endOfDay) OR (i.lastSeen >= :startOfDay AND i.lastSeen < :endOfDay)")
    long countUserForToday(@Param("startOfDay") LocalDateTime startOfDay,
                           @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(ip) FROM IpAddress ip WHERE ip.timeToAdd BETWEEN :startOfDay " +
            "AND :endOfDay AND ip.lastSeen IS NULL")
    long countNewUserForToday(@Param("startOfDay") LocalDateTime startOfDay,
                              @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT ip FROM IpAddress ip WHERE ip.timeToAdd BETWEEN :startOfDay " +
            "AND :endOfDay AND ip.lastSeen IS NULL")
    Page<IpAddress> findNewUsersForToday(@Param("startOfDay") LocalDateTime startOfDay,
                                         @Param("endOfDay") LocalDateTime endOfDay,
                                         Pageable pageable);

    Page<IpAddress> findAllByIsBannedIs(Boolean isBanned, Pageable pageable);

    Page<IpAddress> findAllByAddress(Pageable pageable, String ipAddress);

// Work only for field timeToAdd
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM IpAddress ip WHERE ip.timeToAdd < :cutoffDate AND ip.isBanned = false")
//    void deleteOldIps(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Transactional
    @Modifying
    @Query("""
    DELETE FROM IpAddress ip 
    WHERE (ip.lastSeen < :cutoffDate)
    AND ip.isBanned = false """)
    void deleteOldIps(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Transactional
    @Modifying
    @Query("""
    DELETE FROM IpAddress ip
    WHERE (ip.timeToAdd < :cutoffDate OR ip.lastSeen IS NULL)
    AND ip.isBanned = false""")
    void deleteOldIpsOrIpIsNull(@Param("cutoffDate") LocalDateTime cutoffDate);

}
