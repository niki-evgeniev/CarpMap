package com.example.carpmap.Models.DTO.Reservoirs;

import com.example.carpmap.Models.Enums.FishType;
import com.example.carpmap.Models.Enums.ReservoirType;

import java.time.LocalDateTime;
import java.util.List;

public class ReservoirsDetailsDTO {

    private Long id;

    private String name;

    private String urlName;

    private String city;

    private String information;

    private String description;

    private String urlImage;

    private String latitude;

    private String longitude;

    private String iFrameMap;

    private LocalDateTime createDate;

    private String country;

    private Integer countVisitors;

    private ReservoirType reservoirType;

    private String fishNames;

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

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getiFrameMap() {
        return iFrameMap;
    }

    public void setiFrameMap(String iFrameMap) {
        this.iFrameMap = iFrameMap;
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

    public Integer getCountVisitors() {
        return countVisitors;
    }

    public void setCountVisitors(Integer countVisitors) {
        this.countVisitors = countVisitors;
    }

    public ReservoirType getReservoirType() {
        return reservoirType;
    }

    public void setReservoirType(ReservoirType reservoirType) {
        this.reservoirType = reservoirType;
    }

    public String getFishNames() {
        return fishNames;
    }

    public void setFishNames(String fishNames) {
        this.fishNames = fishNames;
    }

    public List<FishNameDTO> getFishNameDTO() {
        return fishNameDTO;
    }

    public void setFishNameDTO(List<FishNameDTO> fishNameDTO) {
        this.fishNameDTO = fishNameDTO;
    }
}
