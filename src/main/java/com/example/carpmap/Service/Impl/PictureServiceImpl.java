package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirEditGalleryDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirPicturesDTO;
import com.example.carpmap.Models.Entity.Picture;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Repository.PictureRepository;
import com.example.carpmap.Service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_ADD_IMAGE_TO_RESERVOIR;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReservoirPicturesDTO> getAllReservoirPicture(Long id) {
        List<Picture> allByReservoirId = pictureRepository.findAllByReservoirId(id);
        List<ReservoirPicturesDTO> allPicture = allByReservoirId.stream()
                .map(this::reservoirPicturesDTO)
                .toList();
        return allPicture;

    }

    @Override
    public void saveImages(List<String> pictureLink, Reservoir addNewReservoirs) {
        for (String link : pictureLink) {
            if (!link.isBlank()) {
                Picture picture = new Picture();
                picture.setImageURL(link);
                picture.setReservoir(addNewReservoirs);
                pictureRepository.save(picture);
                System.out.printf(SUCCESSFUL_ADD_IMAGE_TO_RESERVOIR, addNewReservoirs.getName());
            }
        }
    }

    @Override
    public void deleteAllListOfPicture(Long reservoirId) {
        List<Picture> allByReservoirId = pictureRepository.findAllByReservoirId(reservoirId);
        pictureRepository.deleteAll(allByReservoirId);
    }

    @Override
    public List<ReservoirEditGalleryDTO> findAllPicture(Long id) {
        List<Picture> allByReservoirId = pictureRepository.findAllByReservoirId(id);
        List<ReservoirEditGalleryDTO> galleryDTOS = allByReservoirId.stream().map(
                res -> {
                    ReservoirEditGalleryDTO galleryDTO = new ReservoirEditGalleryDTO();
                    galleryDTO.setImageUrl(res.getImageURL());
                    galleryDTO.setId(res.getId());
                    return galleryDTO;
                }).toList();
        System.out.println();
        return galleryDTOS;
    }

    private ReservoirPicturesDTO reservoirPicturesDTO(Picture picture) {
        ReservoirPicturesDTO map = modelMapper.map(picture, ReservoirPicturesDTO.class);
        return map;
    }

    private ReservoirEditGalleryDTO reservoirEditGalleryDTO(Picture picture) {
        ReservoirEditGalleryDTO map = modelMapper.map(picture, ReservoirEditGalleryDTO.class);
        return map;
    }
}
