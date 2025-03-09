package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.DTO.Fish.FishDetailsDTO;
import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import com.example.carpmap.Models.Entity.FishList;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.FishListRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Utility.ConvertorBgToEn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FishListServiceImplTest {

    @Mock
    private FishListRepository fishListRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ConvertorBgToEn convertorBgToEn;

    @InjectMocks
    private FishListServiceImpl fishListService;

    private FishList fish;

    @BeforeEach
    void setUp() {
        fish = new FishList();
        fish.setName("Carp");
        fish.setUrlName("carp");
    }

    @Test
    void checkName_ShouldReturnTrue_WhenFishExists() {
        when(fishListRepository.findByName("Carp")).thenReturn(Optional.of(fish));
        assertTrue(fishListService.checkName("Carp"));
    }

    @Test
    void checkName_ShouldReturnFalse_WhenFishDoesNotExist() {
        when(fishListRepository.findByName("Carp")).thenReturn(Optional.empty());
        assertFalse(fishListService.checkName("Carp"));
    }

    @Test
    void addFishList_ShouldSaveFish() {
        AddFishDTO addFishDTO = mock(AddFishDTO.class);
        addFishDTO.setName("testNameDTO");
        addFishDTO.setDescription("Desc Test");
        MultipartFile file = mock(MultipartFile.class);


        when(addFishDTO.getPictureFile()).thenReturn(file);
        when(file.getOriginalFilename()).thenReturn("test.jpg"); // Добави това

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new User()));

        fishListService.addFishList(addFishDTO, userDetails);

        verify(fishListRepository, times(1)).save(any(FishList.class));
    }

    @Test
    void getAll_ShouldReturnList() {
        Page<FishList> page = new PageImpl<>(Collections.singletonList(fish));
        when(fishListRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<FishListAllDTO> result = fishListService.getAll(Pageable.unpaged());

        assertFalse(result.isEmpty());
    }

    @Test
    void getFishListDetails_ShouldReturnDetails_WhenExists() {
        when(fishListRepository.findByUrlName("carp")).thenReturn(Optional.of(fish));
        when(modelMapper.map(any(), eq(FishDetailsDTO.class))).thenReturn(new FishDetailsDTO());

        FishDetailsDTO result = fishListService.getFishListDetails("carp");

        assertNotNull(result);
    }

    @Test
    void deleteFishListDetails_ShouldDeleteFish() {
        when(fishListRepository.findByUrlName("carp")).thenReturn(Optional.of(fish));
        fishListService.deleteFishListDetails("carp");
        verify(fishListRepository, times(1)).delete(any(FishList.class));
    }
}
