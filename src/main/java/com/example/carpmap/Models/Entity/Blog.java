package com.example.carpmap.Models.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "blog")
public class Blog extends BaseEntity{
    @Column(name = "title_blog", nullable = false)
    private String titleBlog;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "url_image", nullable = false)
    private String urlImage;

    @Column(name = "url_video", nullable = false)
    private String urlVideo;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private User user;

    public Blog() {
    }

    public String getTitleBlog() {
        return titleBlog;
    }

    public void setTitleBlog(String titleBlog) {
        this.titleBlog = titleBlog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
