package com.example.carpmap.Models.Entity;

import com.example.carpmap.Models.Enums.FishType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservoirs")
public class Reservoir extends BaseEntity {

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "descriptions", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private User user;

    @ManyToOne
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "fish_types_reservoirs",
            joinColumns = @JoinColumn(name = "reservoirs_id"),
            inverseJoinColumns = @JoinColumn(name = "fish_id"))
    private List<Fish> fish;


    public Reservoir() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Fish> getFish() {
        return fish;
    }

    public void setFish(List<Fish> fish) {
        this.fish = fish;
    }
}
