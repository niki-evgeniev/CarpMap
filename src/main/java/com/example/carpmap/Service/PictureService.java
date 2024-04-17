package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirPicturesDTO;
import com.example.carpmap.Models.Entity.Reservoir;

import java.util.List;

public interface PictureService {

    List<ReservoirPicturesDTO> getAllReservoirPicture(Long id);

    void saveImages(List<String> pictureLink, Reservoir addNewReservoirs);
}
