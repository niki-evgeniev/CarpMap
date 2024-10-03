package com.example.carpmap.Models.DTO.Reservoirs;

import com.example.carpmap.Models.Enums.ReservoirType;

public class ReservoirAllDTO {

    private Long id;

    private String name;

    private String city;

    private String information;

    private String description;

    private String urlImage;

    private String countryCode;

    private String countVisitors;

    private ReservoirType reservoirType;

    public ReservoirAllDTO() {
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountVisitors() {
        return countVisitors;
    }

    public void setCountVisitors(String countVisitors) {
        this.countVisitors = countVisitors;
    }

    public ReservoirType getReservoirType() {
        return reservoirType;
    }

    public void setReservoirType(ReservoirType reservoirType) {
        this.reservoirType = reservoirType;
    }
}
