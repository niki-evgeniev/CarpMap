package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;

public interface FishListService {

    boolean checkName(String fishName);


    void addFishList(AddFishDTO addFishDTO, UserDetails userDetails);
}
