package com.example.carpmap.Models.DTO.Fish;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class AddFishDTO {

    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String name;

    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 3000, message = "Може да въвеждате максимално 2000 символа и минимално 3")
    private String description;

    @NotNull
    private MultipartFile pictureFile;

    private String urlName;

    private String latinName;


    public AddFishDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }
}
