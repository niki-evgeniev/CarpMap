package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import com.example.carpmap.Models.DTO.Reservoirs.FishNameDTO;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FishService {

    void addFishType();

    List<FishNameDTO> getAllFishName();

    List<FishNameDTO> getNonExistingFishType(List<String> fishName);

}
