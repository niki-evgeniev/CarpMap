package com.example.carpmap.Models.DTO.Reservoirs;

public class ReservoirPostGalleryDTO {

    private String id;

    private Long idPic;

    private String imageUrl;

    public ReservoirPostGalleryDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdPic() {
        return idPic;
    }

    public void setIdPic(Long idPic) {
        this.idPic = idPic;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
