package com.example.carpmap.Models.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SearchDTO {

    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String reservoir;

    public SearchDTO() {
    }

    public String getReservoir() {
        return reservoir;
    }

    public void setReservoir(String reservoir) {
        this.reservoir = reservoir;
    }
}
