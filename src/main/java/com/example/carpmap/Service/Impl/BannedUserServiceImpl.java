package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Repository.IpAddressRepository;
import com.example.carpmap.Service.BannedUserService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BannedUserServiceImpl implements BannedUserService {

    private final IpAddressRepository ipAddressRepository;

    public BannedUserServiceImpl(IpAddressRepository ipAddressRepository) {
        this.ipAddressRepository = ipAddressRepository;
    }

    @Override
    public boolean checkIfIpAddressIsBanned(String cloudflareIp) {

        Optional<IpAddress> ipAddress = ipAddressRepository.findByAddress(cloudflareIp);

        if (ipAddress.isPresent()) {
            return ipAddress.get().getBanned();
        }
        return false;
    }
}
