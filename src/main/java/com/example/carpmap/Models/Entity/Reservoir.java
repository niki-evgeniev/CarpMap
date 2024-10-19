package com.example.carpmap.Models.Entity;

import com.example.carpmap.Models.Enums.FishType;
import com.example.carpmap.Models.Enums.ReservoirType;
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

    @Column(name = "url_name", nullable = false, unique = true)
    private String urlName;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "iFrame_map", columnDefinition = "TEXT")
    private String iFrameMap;

    @Column(name = "main_url_image")
    private String mainUrlImage;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "reservoir_type")
    @Enumerated(EnumType.STRING)
    private ReservoirType reservoirType;

    @Column(name = "information", columnDefinition = "TEXT")
    private String information;

    @Column(name = "description", columnDefinition = "TEXT")

    private String description;

    @Column(name = "countVisitors")
    private Integer countVisitors;

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

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
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

    public ReservoirType getReservoirType() {
        return reservoirType;
    }

    public void setReservoirType(ReservoirType reservoirType) {
        this.reservoirType = reservoirType;
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

    public Integer getCountVisitors() {
        return countVisitors;
    }

    public void setCountVisitors(Integer countVisitors) {
        this.countVisitors = countVisitors;
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
