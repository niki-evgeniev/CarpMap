package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirPicturesDTO;

import java.util.List;

public interface PictureService {
    List<ReservoirPicturesDTO> getAllReservoirPicture(Long id);
}
