package com.example.carpmap.Models.DTO;

import jakarta.validation.constraints.NotEmpty;

public class SearchDTO {

    @NotEmpty
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
