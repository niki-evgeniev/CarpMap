package com.example.carpmap.Service;

public interface BannedUserService {

    boolean checkIfIpAddressIsBanned(String cloudflareIp);
}
