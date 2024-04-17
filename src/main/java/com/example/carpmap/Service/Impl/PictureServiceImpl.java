package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Blog.BlogDetailsDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirPicturesDTO;
import com.example.carpmap.Models.Entity.Blog;
import com.example.carpmap.Models.Entity.Picture;
import com.example.carpmap.Repository.PictureRepository;
import com.example.carpmap.Service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

    private ReservoirPicturesDTO reservoirPicturesDTO(Picture picture) {
        ReservoirPicturesDTO map = modelMapper.map(picture, ReservoirPicturesDTO.class);
        return map;
    }
}
