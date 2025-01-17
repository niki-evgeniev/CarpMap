package com.example.carpmap.Models.DTO.Fish;

import java.time.LocalDateTime;

public class FishListAllDTO {

    private String name;

    private String description;

    private String imageUrl;

    private String urlName;

    private String latinName;

    private LocalDateTime addedOnDate;

    public FishListAllDTO() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public LocalDateTime getAddedOnDate() {
        return addedOnDate;
    }

    public void setAddedOnDate(LocalDateTime addedOnDate) {
        this.addedOnDate = addedOnDate;
    }
}
