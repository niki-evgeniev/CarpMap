package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirPicturesDTO;
import com.example.carpmap.Models.Entity.Picture;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Repository.PictureRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PictureServiceImplTest {

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private ReservoirRepository reservoirRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PictureServiceImpl pictureService;

    private Reservoir reservoir;
    private Picture picture;

    @BeforeEach
    void setUp() {
        reservoir = new Reservoir();
        reservoir.setId(1L);
        reservoir.setName("Test Reservoir");

        picture = new Picture();
        picture.setId(1L);
        picture.setImageURL("http://test.com/image.jpg");
        picture.setReservoir(reservoir);
    }

    @Test
    void getAllReservoirPicture_ShouldReturnPictures() {
        when(pictureRepository.findAllByReservoirId(1L)).thenReturn(List.of(picture));
        when(modelMapper.map(any(Picture.class), eq(ReservoirPicturesDTO.class))).thenReturn(new ReservoirPicturesDTO());

        List<ReservoirPicturesDTO> pictures = pictureService.getAllReservoirPicture(1L);

        assertEquals(1, pictures.size());
        verify(pictureRepository, times(1)).findAllByReservoirId(1L);
    }

    @Test
    void saveImages_ShouldSaveValidImages() {
        List<String> imageLinks = List.of("http://test.com/image1.jpg", "http://test.com/image2.jpg");

        pictureService.saveImages(imageLinks, reservoir);

        verify(pictureRepository, times(2)).save(any(Picture.class));
    }

    @Test
    void deleteAllListOfPicture_ShouldDeleteAllImages() {
        when(pictureRepository.findAllByReservoirId(1L)).thenReturn(List.of(picture));

        pictureService.deleteAllListOfPicture(1L);

        verify(pictureRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void getAllReservoirPictureByName_ShouldReturnPictures_WhenReservoirExists() {
        when(reservoirRepository.findByName("Test Reservoir")).thenReturn(Optional.of(reservoir));
        when(pictureRepository.findAllByReservoirId(1L)).thenReturn(List.of(picture));
        when(modelMapper.map(any(Picture.class), eq(ReservoirPicturesDTO.class))).thenReturn(new ReservoirPicturesDTO());

        List<ReservoirPicturesDTO> pictures = pictureService.getAllReservoirPictureByName("Test Reservoir");

        assertNotNull(pictures);
        assertEquals(1, pictures.size());
    }

    @Test
    void getAllReservoirPictureByName_ShouldReturnNull_WhenReservoirDoesNotExist() {
        when(reservoirRepository.findByName("Nonexistent"))
                .thenReturn(Optional.empty());

        List<ReservoirPicturesDTO> pictures = pictureService.getAllReservoirPictureByName("Nonexistent");

        assertNull(pictures);
    }
}
