package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Service.IpAddressService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IpAddressServiceImpl implements IpAddressService {
    @Override
    public String getIp() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getDetails() instanceof WebAuthenticationDetails details) {

            return details.getRemoteAddress();
        }
        return "Cant detect ip";
    }
}
