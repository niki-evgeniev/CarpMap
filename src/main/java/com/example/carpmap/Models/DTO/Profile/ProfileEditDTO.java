package com.example.carpmap.Models.DTO.Profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProfileEditDTO {

    private Long id;

    @NotEmpty(message = "first name information must not be empty")
    @Size(min = 3, max = 30, message = "first name length must be between 3 and 30 character!")
    private String firstName;

    @NotEmpty(message = "Last name information must not be empty")
    @Size(min = 3, max = 30, message = "Last name length must be between 3 and 30 character!")
    private String lastName;

    @NotEmpty(message = "About field must not be empty")
    @Size(min = 3, max = 240, message = "About field length must be between 3 and 240 character!")
    private String about;

    @NotEmpty(message = "Job field must not be empty")
    @Size(min = 3, max = 240, message = "Job field length must be between 3 and 240 character!")
    private String job;

    @NotEmpty(message = "Team field information must not be empty")
    @Size(min = 1, max = 50, message = "Team field length must be between 1 and 50 character!")
    private String team;

    @NotEmpty(message = "Country name field information must not be empty")
    @Size(min = 3, max = 20, message = "Country name field length must be between 3 and 20 character!")
    private String country;

    @NotEmpty(message = "City name field information must not be empty")
    @Size(min = 3, max = 50, message = "City name field length must be between 3 and 50 character!")
    private String city;

    @NotEmpty(message = "Phone name field information must not be empty")
    @Size(min = 3, max = 20, message = "Phone name field length must be between 3 and 20 character!")
    private String phone;

    @NotEmpty(message = "Email name field  information must not be empty")
    @Size(min = 3, max = 20, message = "Email name field length must be between 3 and 20 character!")
    @Email
    private String email;

    @Size(min = 0, max = 200, message = "Facebook name field length must be between 3 and 200 character!")
    private String facebook;

    @Size(min = 0, max = 200, message = "Instagram name field length must be between 3 and 200 character!")
    private String instagram;

    @Size(min = 0, max = 200, message = "LinkedIn name field length must be between 3 and 200 character!")
    private String linkedIn;

    @Size(min = 0, max = 200, message = "Twitter name field length must be between 3 and 200 character!")
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
