package com.example.carpmap.Models.DTO.Profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProfileEditDTO {

    @NotEmpty(message = "first name information must not be empty")
    @Size(min = 3, max = 240, message = "first name length must be between 3 and 20 character!")
    private String firstName;

    @NotEmpty(message = "Last name information must not be empty")
    @Size(min = 3, max = 240, message = "Last name length must be between 3 and 20 character!")
    private String lastName;

    public ProfileEditDTO() {
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
}
