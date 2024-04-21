package com.example.carpmap.Models.DTO.Reservoirs;

import com.example.carpmap.Models.Enums.ReservoirType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ReservoirsEditDTO {

    @NotEmpty(message = "Reservoirs country must not be empty")
    @Size(min = 3, max = 20, message = "Country for Reservoirs length must be between 3 and 20 character!")
    private String country;

    @NotEmpty(message = "Reservoirs city must not be empty")
    @Size(min = 3, max = 20, message = "City for Reservoirs length must be between 3 and 20 character!")
    private String city;

    @NotEmpty(message = "Reservoirs name must not be empty")
    @Size(min = 3, max = 20, message = "Name for Reservoirs length must be between 3 and 20 character!")
    private String name;

    @NotEmpty(message = "Reservoirs latitude must not be empty")
    @Size(min = 3, max = 20, message = "Latitude for Reservoirs length must be between 3 and 20 character!")
    private String latitude;

    @NotEmpty(message = "Reservoirs longitude must not be empty")
    @Size(min = 3, max = 20, message = "Longitude for Reservoirs length must be between 3 and 20 character!")
    private String longitude;

    @NotEmpty(message = "Reservoirs URL Image must not be empty")
    @Size(min = 3, max = 240, message = "URL Image for Reservoirs  length must be between 3 and 20 character!")
    private String mainUrlImage;

    @NotNull
    private ReservoirType reservoirType;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    @NotEmpty(message = "Reservoirs information must not be empty")
    @Size(min = 3, max = 240, message = "information for Reservoirs length must be between 3 and 20 character!")
    private String information;

    @NotEmpty(message = "Reservoirs description must not be empty")
    @Size(min = 3, max = 5000, message = "Description for Reservoirs length must be between 3 and 20 character!")
    private String description;

    @NotEmpty(message = "Select at least 1 type of fish")
    private List<String> fishName;

    public ReservoirsEditDTO() {
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getMainUrlImage() {
        return mainUrlImage;
    }

    public void setMainUrlImage(String mainUrlImage) {
        this.mainUrlImage = mainUrlImage;
    }

    public ReservoirType getReservoirType() {
        return reservoirType;
    }

    public void setReservoirType(ReservoirType reservoirType) {
        this.reservoirType = reservoirType;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFishName() {
        return fishName;
    }

    public void setFishName(List<String> fishName) {
        this.fishName = fishName;
    }
}
