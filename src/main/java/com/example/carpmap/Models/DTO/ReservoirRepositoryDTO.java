package com.example.carpmap.Models.DTO;

public class ReservoirRepositoryDTO {

    private String name;
    private String mainUrlImage;

    public ReservoirRepositoryDTO() {
    }

    public ReservoirRepositoryDTO(String name, String mainUrlImage) {
        this.name = name;
        this.mainUrlImage = mainUrlImage;
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
}
