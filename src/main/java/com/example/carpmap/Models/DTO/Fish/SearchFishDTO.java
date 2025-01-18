package com.example.carpmap.Models.DTO.Fish;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SearchFishDTO {
    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String fishType;

    public SearchFishDTO() {
    }

    public @NotEmpty(message = "Въведете име на язовир")
    String getFishType() {
        return fishType;
    }

    public void setFishType(@NotEmpty(message = "Въведете име на язовир")
    String fishType) {
        this.fishType = fishType;
    }
}
