package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Ip.AllIpDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IpAddressService {

    String getIp();

    Long findAllVisits();

    void checkIpAddressLogin(String username, String ipAddress);

    void getIpVisitor(String ipAddress);

    Page<AllIpDTO> getAllIpsAddress(Pageable pageable);
}
