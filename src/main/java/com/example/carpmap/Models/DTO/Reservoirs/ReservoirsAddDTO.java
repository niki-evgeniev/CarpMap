package com.example.carpmap.Models.DTO.Reservoirs;

import com.example.carpmap.Models.Enums.ReservoirType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ReservoirsAddDTO {

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


    @NotEmpty(message = "Reservoirs iFrameMap must not be empty")
    @Size(min = 3, max = 500, message = "iFrameMap for Reservoirs length must be between 3 and 500 character!")
    private String iFrameMap;


    @NotEmpty(message = "Reservoirs URL Image must not be empty")
    @Size(min = 3, max = 240, message = "URL Image for Reservoirs  length must be between 3 and 20 character!")
    private String mainUrlImage;


    @NotEmpty(message = "Reservoirs URL Image must not be empty")
    @Size(min = 3, max = 240, message = "URL Image for Reservoirs  length must be between 3 and 20 character!")
    private String urlImage2;


    @NotNull
    private String urlImage3;


    @NotNull
    private String urlImage4;


    @NotNull
    private ReservoirType reservoirType;


    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createDate;


    @NotEmpty(message = "Reservoirs information must not be empty")
    @Size(min = 3, max = 240, message = "information for Reservoirs length must be between 3 and 20 character!")
    private String information;


    @NotEmpty(message = "Reservoirs description must not be empty")
    @Size(min = 3, max = 5000, message = "Description for Reservoirs length must be between 3 and 20 character!")
    private String description;

    @NotEmpty(message = "Select at least 1 type of fish")
    private String[] fishName;


    public ReservoirsAddDTO() {
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

    public String getiFrameMap() {
        return iFrameMap;
    }

    public void setiFrameMap(String iFrameMap) {
        this.iFrameMap = iFrameMap;
    }

    public String getMainUrlImage() {
        return mainUrlImage;
    }

    public void setMainUrlImage(String mainUrlImage) {
        this.mainUrlImage = mainUrlImage;
    }

    public String getUrlImage2() {
        return urlImage2;
    }

    public void setUrlImage2(String urlImage2) {
        this.urlImage2 = urlImage2;
    }

    public String getUrlImage3() {
        return urlImage3;
    }

    public void setUrlImage3(String urlImage3) {
        this.urlImage3 = urlImage3;
    }

    public String getUrlImage4() {
        return urlImage4;
    }

    public void setUrlImage4(String urlImage4) {
        this.urlImage4 = urlImage4;
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

    public String[] getFishName() {
        return fishName;
    }

    public void setFishName(String[] fishName) {
        this.fishName = fishName;
    }

}
