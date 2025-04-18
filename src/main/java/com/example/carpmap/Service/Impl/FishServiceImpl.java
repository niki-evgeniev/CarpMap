package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import com.example.carpmap.Models.DTO.Reservoirs.FishNameDTO;
import com.example.carpmap.Models.Entity.Fish;
import com.example.carpmap.Models.Enums.FishType;
import com.example.carpmap.Repository.FishRepository;
import com.example.carpmap.Service.FishService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_ADD_FISH_TYPE;

@Service
public class FishServiceImpl implements FishService {

    private final FishRepository fishRepository;
    private final ModelMapper modelMapper;

    public FishServiceImpl(FishRepository fishRepository, ModelMapper modelMapper) {
        this.fishRepository = fishRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addFishType() {
        if (fishRepository.count() == 0) {
            for (FishType value : FishType.values()) {
                Fish fish = new Fish();
                fish.setFishName(value.name());
                fishRepository.save(fish);
                System.out.printf(SUCCESSFUL_ADD_FISH_TYPE, fish.getFishName());
            }
        }
    }

    @Override
    public List<FishNameDTO> getAllFishName() {
        List<Fish> fishAllName = fishRepository.findAll();
        List<FishNameDTO> fishAllNameDTO = new ArrayList<>();

        for (Fish fish : fishAllName) {
            FishNameDTO fishNameDTO = modelMapper.map(fish, FishNameDTO.class);
            fishAllNameDTO.add(fishNameDTO);
        }
        fishAllNameDTO.sort(Comparator.comparing(FishNameDTO::getFishName));
        return fishAllNameDTO;
    }

    @Override
    public List<FishNameDTO> getNonExistingFishType(List<String> fishName) {
        List<Fish> all = fishRepository.findAll();
        List<FishNameDTO> fishNameDTOS = all.stream()
                .map(this::fishNameDTO)
                .toList();
        return fishNameDTOS;
    }


    private FishNameDTO fishNameDTO(Fish fish) {
        FishNameDTO map = modelMapper.map(fish, FishNameDTO.class);
        return map;
    }
}
