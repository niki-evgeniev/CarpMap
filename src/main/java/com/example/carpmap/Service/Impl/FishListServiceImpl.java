package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.Entity.FishList;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.FishListRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.FishListService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class FishListServiceImpl implements FishListService {
    private final FishListRepository fishListRepository;
    private final UserRepository userRepository;
    private final static String IMAGE_PATH = "./images/fishList/";

    public FishListServiceImpl(FishListRepository fishListRepository, UserRepository userRepository) {
        this.fishListRepository = fishListRepository;
        this.userRepository = userRepository;
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


        try {
            File file = new File(IMAGE_PATH + pictureName);
            file.getParentFile().mkdirs();
            file.createNewFile();

            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(pictureFile.getBytes());
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            FishList fishList = new FishList();
            fishList.setUser(user.get());
            fishList.setImageUrl(IMAGE_PATH + pictureName);
            fishListRepository.save(fishList);
        }
        catch (IOException e ){
            System.out.println(e.getStackTrace());
        }
    }
    private String getPicturePath(MultipartFile pictureFile, String username) {
        String[] pictureName = pictureFile.getOriginalFilename().split("\\.");
        String ext = pictureName[pictureName.length - 1];
        String pathPattern = "%s\\%s." + ext;

        return String.format(pathPattern,
                username,
                UUID.randomUUID());
    }
}
