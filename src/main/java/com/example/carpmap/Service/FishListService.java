package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.DTO.Fish.FishDetailsDTO;
import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface FishListService {

    boolean checkName(String fishName);


    void addFishList(AddFishDTO addFishDTO, UserDetails userDetails);

    Page<FishListAllDTO> getAll(Pageable pageable);

    FishDetailsDTO getFishListDetails(String urlName);

    void deleteFishListDetails(String urlName);
}
