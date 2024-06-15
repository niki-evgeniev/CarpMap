package com.example.carpmap.Models.DTO.Reservoirs;

import java.util.List;

public class EditGalleryDTO {

    Long id;

    List<ReservoirPostGalleryDTO> imageUrl;

    public EditGalleryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ReservoirPostGalleryDTO> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<ReservoirPostGalleryDTO> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
