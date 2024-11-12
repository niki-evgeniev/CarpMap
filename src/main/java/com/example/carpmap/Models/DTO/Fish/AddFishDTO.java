package com.example.carpmap.Models.DTO.Fish;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class AddFishDTO {

    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String fishName;

    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 2000, message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String description;

    private MultipartFile pictureFile;

    private String englishName;

    public AddFishDTO() {
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(MultipartFile pictureFile) {
        this.pictureFile = pictureFile;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}