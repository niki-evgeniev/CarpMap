package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.DTO.Fish.FishDetailsDTO;
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

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String urlName = addFishDTO.getFishName().toLowerCase().trim();
        String engName = convertorBgToEn.convertCyrillicToLatin(urlName);
        try {
            File file = new File(IMAGE_PATH + pictureName);
            file.getParentFile().mkdirs();
            file.createNewFile();

            OutputStream outputStream = new FileOutputStream(file);
            if (pictureFile.isEmpty()){
                System.out.println("EMPTY FILE");
            }
            outputStream.write(pictureFile.getBytes());
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            FishList fishList = new FishList();
            mapNewFishList(addFishDTO, fishList, user, engName, imagePath, pictureName);
            fishListRepository.save(fishList);
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getPicturePath(MultipartFile pictureFile, String username) {
        String[] pictureName = pictureFile.getOriginalFilename().split("\\.");
        String ext = pictureName[pictureName.length - 1];
        String pathPattern = "%s/%s." + ext;

        return String.format(pathPattern,
                username,
                UUID.randomUUID());
    }

    private static void mapNewFishList(AddFishDTO addFishDTO, FishList fishList,
                                       Optional<User> user, String engName,
                                       String imagePath, String pictureName) {
        if (user.isPresent()) {
            User userToAdd = user.get();
            fishList.setUser(userToAdd);
            fishList.setFishName(addFishDTO.getFishName().trim());
            fishList.setAddedOnDate(LocalDateTime.now());
            fishList.setLatinName(addFishDTO.getLatinName().trim());
            fishList.setUrlName(engName);
            fishList.setDescription(addFishDTO.getDescription().trim());
            fishList.setImageUrl(imagePath + pictureName);
        }
    }

    @Override
    public Page<FishListAllDTO> getAll(Pageable pageable) {
        Page<FishList> allFishList = fishListRepository.findAll(pageable);
        for (FishList fishList : allFishList) {
            String description = fishList.getDescription();
            if (description.length() > 200) {
                String getFirst50 = description.substring(0, 200);
                fishList.setDescription(getFirst50);
            }
        }
        Page<FishListAllDTO> allFishListPage = allFishList
                .map(fishList -> {
                    return modelMapper.map(fishList, FishListAllDTO.class);
                });
        return allFishListPage;
    }

    @Override
    public FishDetailsDTO getFishListDetails(String urlName) {
        Optional<FishList> getFishListDetails = fishListRepository.findByUrlName(urlName);
        if (getFishListDetails.isPresent()) {
            FishDetailsDTO fishListDetails = modelMapper.map(getFishListDetails, FishDetailsDTO.class);
            return fishListDetails;
        }
        return null;
    }

    @Override
    public void deleteFishListDetails(String urlName) {
        Optional<FishList> findFishToDelete = fishListRepository.findByUrlName(urlName);
        FishList fishList = findFishToDelete.get();
        String relativePath = "." + fishList.getImageUrl();

        Path filePath = Paths.get(relativePath).toAbsolutePath();
        File file = filePath.toFile();

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Successful delete " + filePath);
                fishListRepository.delete(fishList);
            } else {
                System.out.println("File not found " + filePath);
            }
        } else {
            System.out.println("File not exist " + filePath);
        }
    }
}





