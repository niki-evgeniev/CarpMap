package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Repository.IpAddressRepository;
import com.example.carpmap.Service.BannedUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BannedUserServiceImplTest {

    @Mock
    private IpAddressRepository ipAddressRepository;

    @InjectMocks
    private BannedUserServiceImpl bannedUserService;

    private static final String TEST_IP = "192.168.1.100";

    @BeforeEach
    void setUp() {
    }

    @Test
    void checkIfIpAddressIsBanned_ShouldReturnTrue_WhenIpIsBanned() {
        // Arrange
        IpAddress ipAddress = new IpAddress();
        ipAddress.setAddress(TEST_IP);
        ipAddress.setBanned(true);
        when(ipAddressRepository.findByAddress(TEST_IP)).thenReturn(Optional.of(ipAddress));
        boolean result = bannedUserService.checkIfIpAddressIsBanned(TEST_IP);
        assertTrue(result, "Expected IP to be banned");
    }

    @Test
    void checkIfIpAddressIsBanned_ShouldReturnFalse_WhenIpIsNotBanned() {
        // Arrange
        IpAddress ipAddress = new IpAddress();
        ipAddress.setAddress(TEST_IP);
        ipAddress.setBanned(false);

        when(ipAddressRepository.findByAddress(TEST_IP)).thenReturn(Optional.of(ipAddress));
        boolean result = bannedUserService.checkIfIpAddressIsBanned(TEST_IP);
        assertFalse(result, "Expected IP to not be banned");
    }

    @Test
    void checkIfIpAddressIsBanned_ShouldReturnFalse_WhenIpNotFound() {
        when(ipAddressRepository.findByAddress(TEST_IP)).thenReturn(Optional.empty());
        boolean result = bannedUserService.checkIfIpAddressIsBanned(TEST_IP);
        assertFalse(result, "Expected IP to not be banned when not found");
    }
}
