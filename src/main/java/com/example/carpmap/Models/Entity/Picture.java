package com.example.carpmap.Models.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    @Column(name = "imageurl")
    private String imageURL;

    @Column(name = "image_disk_url")
    private String imageDiskUrl;

    @Column(name = "load_from_disk")
    private boolean loadFromDisk = true;

    @ManyToOne
    private Reservoir reservoir;

    public Picture() {
    }

    public String getImageDiskUrl() {
        return imageDiskUrl;
    }

    public void setImageDiskUrl(String imageDiskUrl) {
        this.imageDiskUrl = imageDiskUrl;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isLoadFromDisk() {
        return loadFromDisk;
    }

    public void setLoadFromDisk(boolean loadFromDisk) {
        this.loadFromDisk = loadFromDisk;
    }

    public Reservoir getReservoir() {
        return reservoir;
    }

    public void setReservoir(Reservoir reservoir) {
        this.reservoir = reservoir;
    }
}


