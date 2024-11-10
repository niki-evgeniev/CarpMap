package com.example.carpmap.Models.DTO.Fish;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AddFishDTO {

    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String fishName;

    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 2000, message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String description;

    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}
