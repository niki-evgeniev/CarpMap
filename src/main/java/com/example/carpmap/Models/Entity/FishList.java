package com.example.carpmap.Models.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "fish_list")
public class FishList extends BaseEntity {

    @Column(name = "fish_name", unique = true, nullable = false)
    private String fishName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "latin_name")
    private String latinName;

    @Column(name = "added_on_date", nullable = false)
    private LocalDateTime addedOnDate;


    @ManyToOne
    private User user;

    public FishList() {
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public LocalDateTime getAddedOnDate() {
        return addedOnDate;
    }

    public void setAddedOnDate(LocalDateTime addedOnDate) {
        this.addedOnDate = addedOnDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
