package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirEditGalleryDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirPicturesDTO;
import com.example.carpmap.Models.Entity.Picture;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Repository.PictureRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import com.example.carpmap.Service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_ADD_IMAGE_TO_RESERVOIR;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ReservoirRepository reservoirRepository;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ReservoirRepository reservoirRepository) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.reservoirRepository = reservoirRepository;
    }

    @Override
    public List<ReservoirPicturesDTO> getAllReservoirPicture(Long id) {
        List<Picture> allByReservoirId = pictureRepository.findAllByReservoirId(id);
//        List<Picture> allByReservoirId = pictureRepository.findAllByReservoirIdAndLoadFromDisk(id, true);
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
        Long reservoirId = id;
        List<Picture> allByReservoirId = pictureRepository.findAllByReservoirId(reservoirId);
        List<ReservoirEditGalleryDTO> galleryDTOS = allByReservoirId.stream().map(
                res -> {
                    ReservoirEditGalleryDTO galleryDTO = new ReservoirEditGalleryDTO();
                    galleryDTO.setId(reservoirId);
                    galleryDTO.setImageUrl(res.getImageURL());
                    galleryDTO.setIdPic(res.getId());
                    return galleryDTO;
                }).toList();

        return galleryDTOS;
    }

    @Override
    public List<ReservoirPicturesDTO> getAllReservoirPictureByName(String name) {
        Optional<Reservoir> reservoirByName = reservoirRepository.findByName(name);

        if (reservoirByName.isPresent()) {
            Long id = reservoirByName.get().getId();
            List<Picture> allByReservoirId = pictureRepository.findAllByReservoirId(id);
            Picture picture = allByReservoirId.get(0);
            if (!picture.isLoadFromDisk()) {
                List<ReservoirPicturesDTO> allPicture = allByReservoirId.stream()
                        .map(this::reservoirPicturesDTO)
                        .toList();
                return allPicture;
            } else {
                List<ReservoirPicturesDTO> allFromDisk = new ArrayList<>();
                for (Picture pic : allByReservoirId) {
                    String imageDiskUrl = pic.getImageDiskUrl();
                    ReservoirPicturesDTO reservoirPicturesDTO = new ReservoirPicturesDTO();
                    reservoirPicturesDTO.setImageURL(imageDiskUrl);
                    allFromDisk.add(reservoirPicturesDTO);
                }
                return allFromDisk;
            }
        }

        System.out.println("Method name: getAllReservoirPictureByName");
        return null;
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
