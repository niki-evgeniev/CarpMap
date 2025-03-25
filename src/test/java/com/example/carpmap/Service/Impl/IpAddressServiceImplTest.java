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
        // Arrange
        SearchIpDTO searchIpDTO = new SearchIpDTO();
        searchIpDTO.setAddress("192.168.0.1");
        
        IpAddress ipAddress = new IpAddress();
        ipAddress.setAddress("192.168.0.1");
        ipAddress.setBanned(false);
        
        AllIpDTO allIpDTO = new AllIpDTO();
        allIpDTO.setAddress("192.168.0.1");
        
        Page<IpAddress> page = new PageImpl<>(List.of(ipAddress));
        when(ipAddressRepository.findAllByAddress(any(Pageable.class), eq("192.168.0.1"))).thenReturn(page);
        when(modelMapper.map(any(IpAddress.class), eq(AllIpDTO.class))).thenReturn(allIpDTO);

        // Act
        Page<AllIpDTO> result = ipAddressService.findByIpAddress(Pageable.unpaged(), searchIpDTO);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("192.168.0.1", result.getContent().get(0).getAddress());
        verify(ipAddressRepository).findAllByAddress(any(Pageable.class), eq("192.168.0.1"));
    }

    @Test
    void getAllIpsAddress_ShouldReturnPageOfIps() {
        // Arrange
        IpAddress ipAddress = new IpAddress();
        ipAddress.setAddress("192.168.0.1");
        
        AllIpDTO allIpDTO = new AllIpDTO();
        allIpDTO.setAddress("192.168.0.1");
        
        Page<IpAddress> page = new PageImpl<>(List.of(ipAddress));
        when(ipAddressRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(IpAddress.class), eq(AllIpDTO.class))).thenReturn(allIpDTO);

        // Act
        Page<AllIpDTO> result = ipAddressService.getAllIpsAddress(Pageable.unpaged());

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("192.168.0.1", result.getContent().get(0).getAddress());
    }

    @Test
    void findLastDayVisitor_ShouldReturnCount() {
        // Arrange
        doReturn(5L).when(ipAddressRepository)
                .countByLastSeenDateTime(any(LocalDateTime.class), any(LocalDateTime.class));

        // Act
        Long result = ipAddressService.findLastDayVisitor();

        // Assert
        assertEquals(5L, result);
    }

    @Test
    void checkIpAddressAndAddToDB_NewIp_ShouldSaveIp() {
        // Arrange
        String newIp = "192.168.0.2";
        when(ipAddressRepository.findByAddress(newIp)).thenReturn(Optional.empty());

        // Act
        ipAddressService.checkIpAddressAndAddToDB(newIp);

        // Assert
        verify(ipAddressRepository).save(argThat(ip -> 
            ip.getAddress().equals(newIp) && 
            ip.getCountVisits() == 1L &&
            !ip.getBanned()
        ));
    }

    @Test
    void findAllBanned_ShouldReturnBannedIps() {
        // Arrange
        IpAddress bannedIp = new IpAddress();
        bannedIp.setAddress("192.168.0.1");
        bannedIp.setBanned(true);
        
        AllIpDTO allIpDTO = new AllIpDTO();
        allIpDTO.setAddress("192.168.0.1");
        
        Page<IpAddress> page = new PageImpl<>(List.of(bannedIp));
        when(ipAddressRepository.findAllByIsBannedIs(eq(true), any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(IpAddress.class), eq(AllIpDTO.class))).thenReturn(allIpDTO);

        // Act
        Page<AllIpDTO> result = ipAddressService.findAllBanned(Pageable.unpaged(), "banned");

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("192.168.0.1", result.getContent().get(0).getAddress());
    }

    @Test
    void findOnlyUsedByUser_ShouldReturnUserIps() {
        // Arrange
        IpAddress userIp = new IpAddress();
        userIp.setAddress("192.168.0.1");
        userIp.setUser(user);
        
        AllIpDTO allIpDTO = new AllIpDTO();
        allIpDTO.setAddress("192.168.0.1");
        
        Page<IpAddress> page = new PageImpl<>(List.of(userIp));
        when(ipAddressRepository.findAllByUserIsNotNull(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(IpAddress.class), eq(AllIpDTO.class))).thenReturn(allIpDTO);

        // Act
        Page<AllIpDTO> result = ipAddressService.findOnlyUsedByUser(Pageable.unpaged(), "user");

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("192.168.0.1", result.getContent().get(0).getAddress());
    }
}
