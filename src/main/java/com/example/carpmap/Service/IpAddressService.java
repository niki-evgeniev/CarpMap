package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Ip.AllIpDTO;
import com.example.carpmap.Models.DTO.Ip.SearchIpDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IpAddressService {

    String getIp();

    Long findAllVisits();

    void checkIpAddressLogin(String username, String ipAddress);

    void getIpVisitor(String ipAddress);

    Page<AllIpDTO> getAllIpsAddress(Pageable pageable);

    boolean banIp(Long id);

    boolean unbanIp(Long id);

    Page<AllIpDTO> findOnlyUsedByUser(Pageable pageable, String type);

    Page<AllIpDTO> findThirtyDaysAgo(Pageable pageable, String type);

    Page<AllIpDTO> findLastDay(Pageable pageable, String type);

    Long findLastDayVisitor();

    Long findNewUsersForToday();

    Page<AllIpDTO> findNewForToday(Pageable pageable, String type);

    Page<AllIpDTO> findAllBanned(Pageable pageable, String type);

    Page<AllIpDTO> findByIpAddress(Pageable pageable, SearchIpDTO searchIpDTO);
}

