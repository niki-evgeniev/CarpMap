package com.example.carpmap.Models.DTO.Fish;

import java.time.LocalDateTime;

public class FishListAllDTO {

    private String fishName;

    private String description;

    private String imageUrl;

    private String englishName;

    private String latinName;

    private LocalDateTime addedOnDate;

    public FishListAllDTO() {
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

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public LocalDateTime getAddedOnDate() {
        return addedOnDate;
    }

    public void setAddedOnDate(LocalDateTime addedOnDate) {
        this.addedOnDate = addedOnDate;
    }
}
