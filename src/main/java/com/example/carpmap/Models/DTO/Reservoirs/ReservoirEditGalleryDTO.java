package com.example.carpmap.Models.DTO.Reservoirs;

public class ReservoirEditGalleryDTO {

    private Long id;

    private Long idPic;

    private String url;

    public ReservoirEditGalleryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPic() {
        return idPic;
    }

    public void setIdPic(Long idPic) {
        this.idPic = idPic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return url;
    }

    public void setImageUrl(String imageUrl) {
        this.url = imageUrl;
    }
}
