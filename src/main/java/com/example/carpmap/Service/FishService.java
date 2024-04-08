package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.FishNameDTO;

import java.util.List;

public interface FishService {

    void addFishType();

    List<FishNameDTO> getAllFishName();
}
