package com.example.carpmap.Service;

public interface IpAddressService {

    String getIp();

    Long findAllVisits();

    void checkIpAddressLogin(String username, String ipAddress);

    void getIpVisitor(String ipAddress);
}
