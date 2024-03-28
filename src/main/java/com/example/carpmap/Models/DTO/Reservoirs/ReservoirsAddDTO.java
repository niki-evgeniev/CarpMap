package com.example.carpmap.Models.DTO.Reservoirs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ReservoirsAddDTO {

    @NotEmpty
    @Size(min = 3, max = 20, message = "Country length must be between 3 and 20 character!")
    private String country;
    @NotEmpty
    @Size(min = 3, max = 20, message = "City length must be between 3 and 20 character!")
    private String city;

    @NotEmpty
    @Size(min = 3, max = 20, message = "Name length must be between 3 and 20 character!")
    private String name;

    @NotEmpty
    @Size(min = 3, max = 20, message = "Latitude length must be between 3 and 20 character!")
    private String latitude;

    @NotEmpty
    @Size(min = 3, max = 20, message = "Longitude length must be between 3 and 20 character!")
    private String longitude;

    @NotEmpty
    @Size(min = 3, max = 240, message = "URL Image length must be between 3 and 20 character!")
    private String urlImage;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createDate;

    @NotEmpty
    @Size(min = 3, max = 240, message = "Description length must be between 3 and 20 character!")
    private String description;

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
