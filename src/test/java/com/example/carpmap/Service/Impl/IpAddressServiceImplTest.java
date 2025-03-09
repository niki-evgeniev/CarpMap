package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Ip.AllIpDTO;
import com.example.carpmap.Models.DTO.Ip.SearchIpDTO;
import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.IpAddressRepository;
import com.example.carpmap.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IpAddressServiceImplTest {

    @Mock
    private IpAddressRepository ipAddressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private IpAddressServiceImpl ipAddressService;

    private IpAddress ipAddress;
    private User user;

    @BeforeEach
    void setUp() {
        ipAddress = new IpAddress();
        ipAddress.setId(1L);
        ipAddress.setAddress("192.168.0.1");
        ipAddress.setCountVisits(1L);
        ipAddress.setTimeToAdd(LocalDateTime.now());
        ipAddress.setLastSeen(LocalDateTime.now());
        ipAddress.setBanned(false);

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
    }

    @Test
    void findAllVisits_ShouldReturnVisitCount() {
        when(ipAddressRepository.sumAllCounts()).thenReturn(10L);

        Long result = ipAddressService.findAllVisits();

        assertEquals(10L, result);
        verify(ipAddressRepository, times(1)).sumAllCounts();
    }

    @Test
    void checkIpAddressWhenUserLogin_NewIp_ShouldSaveIp() {
        when(ipAddressRepository.findByAddress("192.168.0.1")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        ipAddressService.checkIpAddressWhenUserLogin("testUser", "192.168.0.1");

        verify(ipAddressRepository, times(1)).save(any(IpAddress.class));
    }

    @Test
    void checkIpAddressWhenUserLogin_ExistingIp_ShouldUpdateCount() {
        when(ipAddressRepository.findByAddress("192.168.0.1")).thenReturn(Optional.of(ipAddress));
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        ipAddressService.checkIpAddressWhenUserLogin("testUser", "192.168.0.1");

        assertEquals(2L, ipAddress.getCountVisits());
        verify(ipAddressRepository, times(1)).save(ipAddress);
    }

    @Test
    void banIp_ShouldBanIp() {
        when(ipAddressRepository.findById(1L)).thenReturn(Optional.of(ipAddress));

        boolean result = ipAddressService.banIp(1L);

        assertTrue(result);
        assertTrue(ipAddress.getBanned());
        verify(ipAddressRepository, times(1)).save(ipAddress);
    }

    @Test
    void unbanIp_ShouldUnbanIp() {
        ipAddress.setBanned(true);
        when(ipAddressRepository.findById(1L)).thenReturn(Optional.of(ipAddress));

        boolean result = ipAddressService.unbanIp(1L);

        assertTrue(result);
        assertFalse(ipAddress.getBanned());
        verify(ipAddressRepository, times(1)).save(ipAddress);
    }

    @Test
    void findByIpAddress_ShouldReturnMatchingIps() {
        SearchIpDTO searchIpDTO = new SearchIpDTO();
        searchIpDTO.setAddress("192.168.0.1");
        Page<IpAddress> page = new PageImpl<>(List.of(ipAddress));
        when(ipAddressRepository.findAllByAddress(any(Pageable.class), eq("192.168.0.1"))).thenReturn(page);

        Page<AllIpDTO> result = ipAddressService.findByIpAddress(Pageable.unpaged(), searchIpDTO);

        assertFalse(result.isEmpty());
        verify(ipAddressRepository, times(1)).findAllByAddress(any(Pageable.class), eq("192.168.0.1"));
    }
}
