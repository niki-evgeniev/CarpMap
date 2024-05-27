package com.example.carpmap.Models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class SubscribeDTO {

    @Email
    @NotEmpty
    private String email;

    public SubscribeDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
