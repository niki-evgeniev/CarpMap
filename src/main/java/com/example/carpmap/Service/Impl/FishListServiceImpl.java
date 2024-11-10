package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.Entity.FishList;
import com.example.carpmap.Repository.FishListRepository;
import com.example.carpmap.Service.FishListService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FishListServiceImpl implements FishListService {
    private final FishListRepository fishListRepository;

    public FishListServiceImpl(FishListRepository fishListRepository) {
        this.fishListRepository = fishListRepository;
    }

    @Override
    public boolean checkName(String fishName) {
        Optional<FishList> findByFishName = fishListRepository.findByFishName(fishName);
        return findByFishName.isPresent();
    }

    @Override
    public void addFishList(AddFishDTO addFishDTO, UserDetails userDetails) {

    }
}
