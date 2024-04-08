package com.example.carpmap.Models.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "fish_type")
public class Fish extends BaseEntity {

    private String fishName;

    public Fish() {
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }


}
