package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirEditGalleryDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirPicturesDTO;
import com.example.carpmap.Models.Entity.Reservoir;

import java.util.List;

public interface PictureService {

    List<ReservoirPicturesDTO> getAllReservoirPicture(Long id);

    void saveImages(List<String> pictureLink, Reservoir addNewReservoirs);

    void deleteAllListOfPicture(Long reservoirId);

    List<ReservoirEditGalleryDTO> findAllPicture(Long id);

    List<ReservoirPicturesDTO> getAllReservoirPictureByName(String name);
}
