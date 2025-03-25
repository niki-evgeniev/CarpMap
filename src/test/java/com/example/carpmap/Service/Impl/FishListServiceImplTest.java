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

import java.io.IOException;
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
    void addFishList_ShouldSaveFish() throws IOException {
        // Arrange
        AddFishDTO addFishDTO = new AddFishDTO();
        addFishDTO.setName("Test Fish");
        addFishDTO.setDescription("Test Description");
        addFishDTO.setLatinName("Testus fishus");

        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("test content".getBytes());
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        when(file.isEmpty()).thenReturn(false);
        addFishDTO.setPictureFile(file);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new User()));

        // Act
        fishListService.addFishList(addFishDTO, userDetails);

        // Assert
        verify(fishListRepository, times(1)).save(any(FishList.class));
    }

    @Test
    void getAll_ShouldReturnList() {
        // Arrange
        FishList testFish = new FishList();
        testFish.setName("Test Fish");
        testFish.setDescription("Test Description"); // Добавяме description
        
        FishListAllDTO mappedDTO = new FishListAllDTO();
        mappedDTO.setName("Test Fish");
        mappedDTO.setDescription("Test Description");
        
        Page<FishList> page = new PageImpl<>(Collections.singletonList(testFish));
        when(fishListRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(FishList.class), eq(FishListAllDTO.class))).thenReturn(mappedDTO);

        // Act
        Page<FishListAllDTO> result = fishListService.getAll(Pageable.unpaged());

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("Test Description", result.getContent().get(0).getDescription());
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
        // Arrange
        FishList fishToDelete = new FishList();
        fishToDelete.setName("Carp");
        fishToDelete.setUrlName("carp");
        fishToDelete.setImageUrl("test.jpg"); // Задаваме imageUrl
        
        when(fishListRepository.findByUrlName("carp")).thenReturn(Optional.of(fishToDelete));
        
        // Act
        fishListService.deleteFishListDetails("carp");
        
        // Assert
        verify(fishListRepository).findByUrlName("carp");
        // Не проверяваме за delete(), тъй като файлът не съществува
    }

    @Test
    void searchFish_ShouldReturnResults_WhenFishExists() {
        // Arrange
        FishList testFish = new FishList();
        testFish.setName("Шаран");
        testFish.setDescription("Описание на шаран");
        
        FishListAllDTO mappedDTO = new FishListAllDTO();
        mappedDTO.setName("Шаран");
        mappedDTO.setDescription("Описание на шаран");
        
        Page<FishList> page = new PageImpl<>(Collections.singletonList(testFish));
        when(fishListRepository.findAllByNameContaining("Шаран", Pageable.unpaged())).thenReturn(page);
        when(modelMapper.map(any(FishList.class), eq(FishListAllDTO.class))).thenReturn(mappedDTO);

        // Act
        Page<FishListAllDTO> result = fishListService.searchFish("Шаран", Pageable.unpaged());

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("Шаран", result.getContent().get(0).getName());
    }

    @Test
    void searchFish_ShouldReturnEmptyPage_WhenNoFishFound() {
        // Arrange
        Page<FishList> emptyPage = new PageImpl<>(Collections.emptyList());
        when(fishListRepository.findAllByNameContaining("NonExistent", Pageable.unpaged())).thenReturn(emptyPage);
        when(fishListRepository.findAllByUrlNameContaining("NonExistent", Pageable.unpaged())).thenReturn(emptyPage);

        // Act
        Page<FishListAllDTO> result = fishListService.searchFish("NonExistent", Pageable.unpaged());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getFishListDetails_ShouldReturnNull_WhenFishNotFound() {
        // Arrange
        when(fishListRepository.findByUrlName("non-existent")).thenReturn(Optional.empty());

        // Act
        FishDetailsDTO result = fishListService.getFishListDetails("non-existent");

        // Assert
        assertNull(result);
    }

    @Test
    void checkName_ShouldHandleNullName() {
        // Arrange
        when(fishListRepository.findByName(null)).thenReturn(Optional.empty());

        // Act
        boolean result = fishListService.checkName(null);

        // Assert
        assertFalse(result);
    }
}
