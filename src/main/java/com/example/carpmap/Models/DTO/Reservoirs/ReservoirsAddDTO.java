package com.example.carpmap.Models.DTO.Reservoirs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ReservoirsAddDTO {

    @NotEmpty(message = "Name cannot be empty!")
    @Size(min = 3, max = 20, message = "Name length must be between 3 and 20 character!")
    private String name;

    @NotEmpty(message = "latitude cannot be empty!")
    @Size(min = 3, max = 20, message = "latitude length must be between 3 and 20 character!")
    private String latitude;

    @NotEmpty(message = "longitude cannot be empty!")
    @Size(min = 3, max = 20, message = "longitude length must be between 3 and 20 character!")
    private String longitude;

    @NotEmpty(message = "urlImage cannot be empty!")
    @Size(min = 3, max = 20, message = "urlImage length must be between 3 and 20 character!")
    private String urlImage;

    private LocalDateTime createDate;

    @NotEmpty(message = "description cannot be empty!")
    @Size(min = 3, max = 20, message = "description length must be between 3 and 20 character!")
    private String description;

    public ReservoirsAddDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
