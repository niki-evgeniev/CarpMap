package com.example.carpmap.Models.DTO.Reservoirs;

import com.example.carpmap.Models.Enums.FishType;
import com.example.carpmap.Models.Enums.ReservoirType;

import java.time.LocalDateTime;
import java.util.List;

public class ReservoirsDetailsDTO {

    private Long id;

    private String name;

    private String city;

    private String description;

    private String urlImage;

    private String latitude;

    private String longitude;

    private LocalDateTime createDate;

    private String country;

    private ReservoirType reservoirType;

    private List<FishNameDTO> fishNameDTO;

    public ReservoirsDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ReservoirType getReservoirType() {
        return reservoirType;
    }

    public void setReservoirType(ReservoirType reservoirType) {
        this.reservoirType = reservoirType;
    }

    public List<FishNameDTO> getFishNameDTO() {
        return fishNameDTO;
    }

    public void setFishNameDTO(List<FishNameDTO> fishNameDTO) {
        this.fishNameDTO = fishNameDTO;
    }
}
