package com.example.carpmap.Models.DTO;

public class ReservoirRepositoryDTO {

    private String name;
    private String mainUrlImage;
    private String urlName;

    public ReservoirRepositoryDTO() {
    }

    public ReservoirRepositoryDTO(String name, String mainUrlImage, String urlName) {
        this.name = name;
        this.mainUrlImage = mainUrlImage;
        this.urlName = urlName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainUrlImage() {
        return mainUrlImage;
    }

    public void setMainUrlImage(String mainUrlImage) {
        this.mainUrlImage = mainUrlImage;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }
}
