package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import com.example.carpmap.Models.Entity.FishList;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.FishListRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.FishListService;
import com.example.carpmap.Utility.ConvertorBgToEn;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class FishListServiceImpl implements FishListService {
    private final FishListRepository fishListRepository;
    private final UserRepository userRepository;
    private final static String IMAGE_PATH = "./imagesApp/fishList/";
    private final ModelMapper modelMapper;
    private final ConvertorBgToEn convertorBgToEn;

    public FishListServiceImpl(FishListRepository fishListRepository, UserRepository userRepository,
                               ModelMapper modelMapper, ConvertorBgToEn convertorBgToEn) {
        this.fishListRepository = fishListRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.convertorBgToEn = convertorBgToEn;
    }

    @Override
    public boolean checkName(String fishName) {
        Optional<FishList> findByFishName = fishListRepository.findByFishName(fishName);
        return findByFishName.isPresent();
    }

    @Override
    public void addFishList(AddFishDTO addFishDTO, UserDetails userDetails) {
        MultipartFile pictureFile = addFishDTO.getPictureFile();
        String pictureName = getPicturePath(pictureFile, userDetails.getUsername());
        String imagePath = IMAGE_PATH.substring(1);
        String engName = convertorBgToEn.convertCyrillicToLatin(addFishDTO.getFishName()).toLowerCase();

        try {
            File file = new File(IMAGE_PATH + pictureName);
            file.getParentFile().mkdirs();
            file.createNewFile();

            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(pictureFile.getBytes());
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            FishList fishList = new FishList();
            fishList.setUser(user.get());
            fishList.setFishName(addFishDTO.getFishName());
            fishList.setAddedOnDate(LocalDateTime.now());
            fishList.setLatinName(addFishDTO.getLatinName());
            fishList.setUrlName(engName);
            fishList.setDescription(addFishDTO.getDescription());
            fishList.setImageUrl(imagePath + pictureName);
            fishListRepository.save(fishList);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    @Override
    public Page<FishListAllDTO> getAll(Pageable pageable) {
        Page<FishList> allFishList = fishListRepository.findAll(pageable);
        for (FishList fishList : allFishList) {
            String description = fishList.getDescription();
            if (description.length() > 500) {
                String getFirst50 = description.substring(1, 500);
                fishList.setDescription(getFirst50);
            }
        }
        Page<FishListAllDTO> allFishListPage = allFishList
                .map(fishList -> {
                    return modelMapper.map(fishList, FishListAllDTO.class);
                });
        return allFishListPage;
    }

    private String getPicturePath(MultipartFile pictureFile, String username) {
        String[] pictureName = pictureFile.getOriginalFilename().split("\\.");
        String ext = pictureName[pictureName.length - 1];
        String pathPattern = "%s/%s." + ext;

        return String.format(pathPattern,
                username,
                UUID.randomUUID());
    }
}
