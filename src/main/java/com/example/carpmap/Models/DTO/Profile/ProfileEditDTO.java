package com.example.carpmap.Models.DTO.Profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProfileEditDTO {

    private Long id;

    @NotEmpty(message = "first name information must not be empty")
    @Size(min = 3, max = 240, message = "first name length must be between 3 and 20 character!")
    private String firstName;

    @NotEmpty(message = "Last name information must not be empty")
    @Size(min = 3, max = 240, message = "Last name length must be between 3 and 20 character!")
    private String lastName;

    private String about;

    private String job;

    private String team;

    private String country;

    private String phone;

    private String email;

    private String facebook;

    private String instagram;

    private String linkedIn;

    private String twitter;

    public ProfileEditDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
